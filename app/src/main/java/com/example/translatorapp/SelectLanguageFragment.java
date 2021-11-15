package com.example.translatorapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.translatorapp.adapters.LanguagesAdapter;
import com.example.translatorapp.models.Languagesmodel;

import java.util.ArrayList;
import java.util.Locale;


public class SelectLanguageFragment extends Fragment {

    RecyclerView allLanguageRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_language, container, false);

        allLanguageRecyclerView = view.findViewById(R.id.AllLanguageRecyclerView);

        ArrayList<Languagesmodel> list = new ArrayList<>();
        list.add( new Languagesmodel("marathi","d",true,"Mr"));
        list.add(new Languagesmodel("gujrati","f",true,"Guj"));
        list.add(new Languagesmodel("hindi","g",true,"Tm"));
        list.add(new Languagesmodel("english","g",true,"Tm"));

        String lang="";
        String FromLang="";
        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.getString("From").equals("tranlatefrom")){
                lang= bundle.getString("TranslateTo").toLowerCase();
                FromLang=bundle.getString("FromLang").toLowerCase();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getLang().equals(FromLang)){
                        list.remove(i);
                    }
                }
            }else{
                lang = bundle.getString("Language").toLowerCase();
            }
        }



        LanguagesAdapter languagesAdapter=new LanguagesAdapter(list,lang,FromLang);
        allLanguageRecyclerView.setAdapter(languagesAdapter);

        return view;
    }
}