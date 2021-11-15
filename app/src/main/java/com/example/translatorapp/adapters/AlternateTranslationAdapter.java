package com.example.translatorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;
import com.example.translatorapp.WriteActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.List;

public class AlternateTranslationAdapter extends RecyclerView.Adapter<AlternateTranslationAdapter.ViewHolder> {

    List<RecentlyTranslatedLanguages> list=new ArrayList<>();
    String toTrans;
    int fromLanguageCode,toLangCode;
    public AlternateTranslationAdapter(List<RecentlyTranslatedLanguages> list,String toTranslate) {
        this.list=list;
        this.toTrans=toTranslate;

        fromLanguageCode=getLanguageCode(list.get(0).getFromLang());
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

    @NonNull
    @Override
    public AlternateTranslationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.alternate_translation_recycler_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlternateTranslationAdapter.ViewHolder holder, int position) {

            toLangCode=getLanguageCode(list.get(position).getToLang());
        holder.fromlang.setText(list.get(position).getToLang());

        FirebaseTranslatorOptions firebaseTranslatorOptions=new FirebaseTranslatorOptions.Builder().setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLangCode).build();

        FirebaseTranslator firebaseTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(firebaseTranslatorOptions);

        FirebaseModelDownloadConditions firebaseModelDownloadConditions=new FirebaseModelDownloadConditions.Builder().build();

        firebaseTranslator.downloadModelIfNeeded(firebaseModelDownloadConditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                firebaseTranslator.translate(toTrans).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        holder.translation.setText(s);

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromlang,translation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromlang=itemView.findViewById(R.id.alternatelangfrom);
            translation=itemView.findViewById(R.id.alternatelangtranslation);
        }
    }
}
