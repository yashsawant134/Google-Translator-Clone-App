package com.example.translatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;
import com.example.translatorapp.adapters.RecentTranslateAdapter;
import com.example.translatorapp.models.RecentTranslateModel;
import com.example.translatorapp.viewmodel.WriteActivityViewmodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    EditText EnterTextToTranslateEditText;
    TextView TranslateTo;
    ImageView goBtn;
    RecyclerView RecentTranlationRecyclerView;
    LinearLayout TranslateFromLinearLayout;
    int tranlateFromLanguageCode,tranlateToLanguageCode;
    WriteActivityViewmodel writeActivityViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        getSupportActionBar().hide();


        String TranslationFrom=getIntent().getStringExtra("Translatefrm").toLowerCase(Locale.ROOT);
        String TranslationTo=getIntent().getStringExtra("Translation").toLowerCase(Locale.ROOT);

        tranlateFromLanguageCode=getLanguageCode(TranslationFrom);
        tranlateToLanguageCode=getLanguageCode(TranslationTo);



        EnterTextToTranslateEditText=findViewById(R.id.entertextToTranslate);
        TranslateTo=findViewById(R.id.traslateToTextView);
        goBtn=findViewById(R.id.goBtn);
        RecentTranlationRecyclerView=findViewById(R.id.RecentTranslationRecyclerView);
        TranslateFromLinearLayout = findViewById(R.id.TranslateFromLinearLayout);

        EnterTextToTranslateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TranslateTo.setText(s.toString());
                TranslateText(tranlateFromLanguageCode,tranlateToLanguageCode,s.toString());

                if(s.toString().length()==0){
                    goBtn.setVisibility(View.GONE);
                    RecentTranlationRecyclerView.setVisibility(View.VISIBLE);
                }else{
                    goBtn.setVisibility(View.VISIBLE);
                    RecentTranlationRecyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







        TranslateFromLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TranslateTo.getText().toString().equals("")){

                     writeActivityViewmodel.insertList(new RecentSearch(TranslationFrom,TranslationTo,TranslateTo.getText().toString(),EnterTextToTranslateEditText.getText().toString(),""));
                     writeActivityViewmodel.insertRecentTranslatedList(new RecentlyTranslatedLanguages(TranslationFrom,TranslationTo));
                    Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                    intent.putExtra("Translation",TranslateTo.getText().toString());
                    intent.putExtra("Translatefrm",EnterTextToTranslateEditText.getText().toString());
                    intent.putExtra("TranslatefrmLang",TranslationFrom);
                    intent.putExtra("TranslatetoLang",TranslationTo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });


        writeActivityViewmodel=new ViewModelProvider(this).get(WriteActivityViewmodel.class);
        writeActivityViewmodel.getRecentSearchList(TranslationFrom,TranslationTo).observe(this, new Observer<List<RecentSearch>>() {
            @Override
            public void onChanged(List<RecentSearch> recentSearches) {
                RecentTranslateAdapter recentTranslateAdapter=new RecentTranslateAdapter(recentSearches);
                RecentTranlationRecyclerView.setAdapter(recentTranslateAdapter);

            }
        });

    }

    public int getLanguageCode(String s){

        int LanguageCode= 0;

        switch(s){
            case "english":
                LanguageCode= FirebaseTranslateLanguage.EN;
                break;
            case "marathi":
                LanguageCode= FirebaseTranslateLanguage.MR;
                break;
            case "gujrati":
                LanguageCode= FirebaseTranslateLanguage.GU;
                break;
            case "hindi":
                LanguageCode= FirebaseTranslateLanguage.HI;
                break;

        }

        return LanguageCode;
    }


    public void TranslateText(int fromLanguageCode,int ToLanguageCode,String text){
        FirebaseTranslatorOptions firebaseTranslatorOptions=new FirebaseTranslatorOptions.Builder().setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(ToLanguageCode).build();

        FirebaseTranslator firebaseTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(firebaseTranslatorOptions);

        FirebaseModelDownloadConditions firebaseModelDownloadConditions=new FirebaseModelDownloadConditions.Builder().build();

        firebaseTranslator.downloadModelIfNeeded(firebaseModelDownloadConditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                firebaseTranslator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        TranslateTo.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WriteActivity.this,"FAiled",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WriteActivity.this,"FAiled to download model",Toast.LENGTH_SHORT).show();
            }
        });
    }

}