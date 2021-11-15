package com.example.translatorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.ROOM.tables.RecentSearch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class HomeHistoryRecyclerAdapter extends RecyclerView.Adapter<HomeHistoryRecyclerAdapter.ViewHolder> {

    List<RecentSearch> list =new ArrayList<>();
    public HomeHistoryRecyclerAdapter(List<RecentSearch> list) {
        this.list= list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_todays_translatedhistory,parent,false);
        return new HomeHistoryRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.translateTo.setText(list.get(position).getTranslationFromText());
        holder.translateFrom.setText(list.get(position).getTrsanslationToText());
    }

    @Override
    public int getItemCount() {
        if(list.isEmpty()){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView translateFrom,translateTo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            translateFrom=itemView.findViewById(R.id.homehistorytraslatefromtext);
            translateTo=itemView.findViewById(R.id.homehistorytraslatetoText);
        }
    }
}
