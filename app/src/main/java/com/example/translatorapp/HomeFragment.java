package com.example.translatorapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;
import com.example.translatorapp.adapters.AlternateTranslationAdapter;
import com.example.translatorapp.adapters.HomeHistoryRecyclerAdapter;
import com.example.translatorapp.adapters.RecentTranslateAdapter;
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

public class HomeFragment extends Fragment {

    LinearLayout TranslateFromLinearLayout,TranslateToLangLinearLayout,editTextSpeakerIcon,micicon;
    TextView FromLangText,TranslateToLangText,homeTranslation,translationLangTextView;
    EditText EnterToTranslateEditText;
    ImageView TranslateIcon;
    CardView translatebody,AlternateTranslteCardeView;
    WriteActivityViewmodel writeActivityViewmodel;
    ArrayList<RecentlyTranslatedLanguages> recentlyTranslatedLanguageslist;
    RecyclerView alternateTranslateRectclerview;
    TextToSpeech textToSpeech;
    RecyclerView HomeHistoryREcyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        TranslateFromLinearLayout = view.findViewById(R.id.fromLang);
        FromLangText=view.findViewById(R.id.fromLangText);
        TranslateToLangLinearLayout= view.findViewById(R.id.TranslateToLangLinearLayout);
        TranslateToLangText=view.findViewById(R.id.TranslateToLangText);
        EnterToTranslateEditText = view.findViewById(R.id.EnterToTranslateEditText);
        editTextSpeakerIcon= view.findViewById(R.id.speakericon);
        TranslateIcon= view.findViewById(R.id.translateIcon);
        homeTranslation=view.findViewById(R.id.homeTranslation);
        translatebody=view.findViewById(R.id.translatebody);
        translationLangTextView=view.findViewById(R.id.translationLangTextView);
        micicon=view.findViewById(R.id.micicon);
        AlternateTranslteCardeView=view.findViewById(R.id.AlternateTranslteCardeView);
        alternateTranslateRectclerview=view.findViewById(R.id.AlternateTranslteRecyclerView);
        HomeHistoryREcyclerView=view.findViewById(R.id.homeHistoryRecyclerView);

        if(getActivity().getIntent().getExtras()==null){
            FromLangText.setText("English");
            TranslateToLangText.setText("Hindi");

        }else{

            translationLangTextView.setText(getActivity().getIntent().getStringExtra("TranslatetoLang"));
            EnterToTranslateEditText.setText(getActivity().getIntent().getStringExtra("Translatefrm"));
            homeTranslation.setText(getActivity().getIntent().getStringExtra("Translation"));
            FromLangText.setText(getActivity().getIntent().getStringExtra("TranslatefrmLang"));
            TranslateToLangText.setText(getActivity().getIntent().getStringExtra("TranslatetoLang"));
            if(getActivity().getIntent().hasExtra("TranslatetoLang")){
                translatebody.setVisibility(View.VISIBLE);
                HomeHistoryREcyclerView.setVisibility(View.GONE);
            }

            writeActivityViewmodel=new ViewModelProvider(this).get(WriteActivityViewmodel.class);
            writeActivityViewmodel.getRecentTranslatedLanguages(FromLangText.getText().toString()).observe(getActivity(), new Observer<List<RecentlyTranslatedLanguages>>() {
                @Override
                public void onChanged(List<RecentlyTranslatedLanguages> recentlyTranslatedLanguages) {
                    recentlyTranslatedLanguageslist=new ArrayList<>();
                    List<String> ContainToLang=new ArrayList<>();
                    for(int i=0;i<recentlyTranslatedLanguages.size();i++){
                        if(!recentlyTranslatedLanguages.get(i).getToLang().equals(TranslateToLangText.getText().toString()) && !ContainToLang.contains(recentlyTranslatedLanguages.get(i).getToLang())){
                            ContainToLang.add(recentlyTranslatedLanguages.get(i).getToLang());
                            Log.i("yash",recentlyTranslatedLanguages.get(i).getToLang() +" " +TranslateToLangText.getText().toString() );
                            recentlyTranslatedLanguageslist.add(new RecentlyTranslatedLanguages(FromLangText.getText().toString(),recentlyTranslatedLanguages.get(i).getToLang()));
                        }
                    }

                    if(recentlyTranslatedLanguageslist.size()>0){
                        AlternateTranslteCardeView.setVisibility(View.VISIBLE);
                        AlternateTranslationAdapter alternateTranslationAdapter=new AlternateTranslationAdapter(recentlyTranslatedLanguageslist,EnterToTranslateEditText.getText().toString());
                        alternateTranslateRectclerview.setAdapter(alternateTranslationAdapter);
                    }
                }
            });

            getActivity().getIntent().removeExtra("TranslatetoLang");
            getActivity().getIntent().removeExtra("Translatefrm");
            getActivity().getIntent().removeExtra("Translation");
            getActivity().getIntent().removeExtra("TranslatefrmLang");
            getActivity().getIntent().removeExtra("TranslatetoLang");
        }

        if(FromLangText.getText().toString().equals("") && TranslateToLangText.getText().toString().equals("") ){
            FromLangText.setText("English");
            TranslateToLangText.setText("Hindi");
        }


        Bundle bundle=getArguments();

        if(bundle!=null){
            if(bundle.getString("FromLang").equals("")){
                String lang= bundle.getString("Language").toUpperCase();
                FromLangText.setText(lang);
            }else{
                TranslateToLangText.setText(bundle.getString("Language").toUpperCase());
            }

        }


        TranslateFromLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectLang = new SelectLanguageFragment();

                Bundle args = new Bundle();
                args.putString("Language",FromLangText.getText().toString());
                args.putString("From","tranlate");
                SelectLang.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,SelectLang).commit();
            }
        });


        TranslateToLangLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment SelectLang = new SelectLanguageFragment();

                Bundle args = new Bundle();
                args.putString("FromLang",FromLangText.getText().toString());
                args.putString("TranslateTo",TranslateToLangText.getText().toString());
                args.putString("From","tranlatefrom");

                SelectLang.setArguments(args);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,SelectLang).commit();
            }
        });



        EnterToTranslateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WriteActivity.class);
                intent.putExtra("Translation",TranslateToLangText.getText().toString());
                intent.putExtra("Translatefrm",FromLangText.getText().toString());
                startActivity(intent);
            }
        });

        EnterToTranslateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editTextSpeakerIcon.setVisibility(View.GONE);
                }
            }
        });

        TranslateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterToTranslateEditText.setFocusable(false);
                editTextSpeakerIcon.setVisibility(View.VISIBLE);
            }
        });




        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        micicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromlang=FromLangText.getText().toString().toLowerCase(Locale.ROOT);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                switch (fromlang){
                    case "english":
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "eng");
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "eng");
                        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "eng");
                        break;

                    case "marathi":
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "mr");
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "mr");
                        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "mr");
                        break;

                    case "hindi":
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi");
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "hi");
                        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "hi");
                        break;

                    case "gujrati":
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu");
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "gu");
                        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "gu");
                        break;

                }
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to convert it into text");

                try {
                    startActivityForResult(intent,1);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });



        textToSpeech=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);

                    if(result==textToSpeech.LANG_MISSING_DATA || result== TextToSpeech.LANG_NOT_SUPPORTED){
                        
                    }
                }
            }
        });


        editTextSpeakerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(homeTranslation.getText().toString());

            }
        });



        writeActivityViewmodel=new ViewModelProvider(this).get(WriteActivityViewmodel.class);
        writeActivityViewmodel.getAllHistory().observe(getActivity(), new Observer<List<RecentSearch>>() {
            @Override
            public void onChanged(List<RecentSearch> recentSearches) {
                List<RecentSearch> six_recentSearches=new ArrayList<>();
                if(recentSearches.size()>0){

                    for(int i=0;i<=recentSearches.size()-1;i++){
                        if(i<=5){

                            six_recentSearches.add(recentSearches.get(i));
                        }
                    }
                    HomeHistoryRecyclerAdapter homeHistoryRecyclerAdapter=new HomeHistoryRecyclerAdapter(six_recentSearches);
                    HomeHistoryREcyclerView.setAdapter(homeHistoryRecyclerAdapter);
                    HomeHistoryREcyclerView.suppressLayout(true);
                }
            }
        });

    }

    public void speak(String s){
        textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            if(resultCode==RESULT_OK && data!=null){
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                EnterToTranslateEditText.setText(result.get(0));

                writeActivityViewmodel=new ViewModelProvider(this).get(WriteActivityViewmodel.class);
                writeActivityViewmodel.getRecentTranslatedLanguages(FromLangText.getText().toString()).observe(getActivity(), new Observer<List<RecentlyTranslatedLanguages>>() {
                    @Override
                    public void onChanged(List<RecentlyTranslatedLanguages> recentlyTranslatedLanguages) {
                        recentlyTranslatedLanguageslist=new ArrayList<>();
                        List<String> ContainToLang=new ArrayList<>();
                        for(int i=0;i<recentlyTranslatedLanguages.size();i++){
                            if(!recentlyTranslatedLanguages.get(i).getToLang().equals(TranslateToLangText.getText().toString()) && !ContainToLang.contains(recentlyTranslatedLanguages.get(i).getToLang())){
                                ContainToLang.add(recentlyTranslatedLanguages.get(i).getToLang());
                                Log.i("yash",recentlyTranslatedLanguages.get(i).getToLang() +" " +TranslateToLangText.getText().toString() );
                                recentlyTranslatedLanguageslist.add(new RecentlyTranslatedLanguages(FromLangText.getText().toString(),recentlyTranslatedLanguages.get(i).getToLang()));
                            }
                        }

                        if(recentlyTranslatedLanguageslist.size()>0){
                            AlternateTranslteCardeView.setVisibility(View.VISIBLE);
                            AlternateTranslationAdapter alternateTranslationAdapter=new AlternateTranslationAdapter(recentlyTranslatedLanguageslist,EnterToTranslateEditText.getText().toString());
                            alternateTranslateRectclerview.setAdapter(alternateTranslationAdapter);
                        }
                    }
                });


                int fromlangCode=getLanguageCode(FromLangText.getText().toString().toLowerCase(Locale.ROOT));
                int toLangCode=getLanguageCode(TranslateToLangText.getText().toString().toLowerCase(Locale.ROOT));


                FirebaseTranslatorOptions firebaseTranslatorOptions=new FirebaseTranslatorOptions.Builder().setSourceLanguage(fromlangCode)
                        .setTargetLanguage(toLangCode).build();

                FirebaseTranslator firebaseTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(firebaseTranslatorOptions);

                FirebaseModelDownloadConditions firebaseModelDownloadConditions=new FirebaseModelDownloadConditions.Builder().build();

                firebaseTranslator.downloadModelIfNeeded(firebaseModelDownloadConditions).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firebaseTranslator.translate(result.get(0)).addOnSuccessListener(new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String s) {
                                translatebody.setVisibility(View.VISIBLE);
                                HomeHistoryREcyclerView.setVisibility(View.GONE);
                               homeTranslation.setText(s);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

            }
        }
    }

    @Override
    public void onDestroy() {

        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();


    }
}