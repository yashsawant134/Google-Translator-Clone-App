package com.example.translatorapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.HomeFragment;
import com.example.translatorapp.R;
import com.example.translatorapp.SelectLanguageFragment;
import com.example.translatorapp.models.Languagesmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    List<Languagesmodel> list = new ArrayList<>();
    String LanguageSelected, FromLang = "";

    public LanguagesAdapter(List<Languagesmodel> list, String Languageselected, String FromLang) {
        this.list = list;
        this.LanguageSelected = Languageselected;
        this.FromLang = FromLang;
    }

    @NonNull
    @Override
    public LanguagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_row_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguagesAdapter.ViewHolder holder, int position) {

//        if (!FromLang.equals(list.get(position).getLang())) {

            holder.LangName.setText(list.get(position).getLang());
            if (LanguageSelected.toLowerCase(Locale.ROOT).equals(list.get(position).getLang().toLowerCase(Locale.ROOT))) {
                String a = list.get(position).getLang();
                holder.check.setVisibility(View.VISIBLE);
                holder.downloadicon.setVisibility(View.GONE);
            } else {
                holder.check.setVisibility(View.GONE);
                holder.downloadicon.setVisibility(View.VISIBLE);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment Home = new HomeFragment();

                    Bundle args = new Bundle();
                    args.putString("Language", holder.LangName.getText().toString());
                    args.putString("FromLang",FromLang);
                    Home.setArguments(args);

                    Fragment selectLangugage = new SelectLanguageFragment();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().remove(selectLangugage).replace(R.id.fragmentContainer, Home).commit();

                }
            });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView LangName;
        ImageView check, downloadicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            LangName = itemView.findViewById(R.id.langname);
            check = itemView.findViewById(R.id.check);
            downloadicon = itemView.findViewById(R.id.downloadicon);


        }


    }
}
