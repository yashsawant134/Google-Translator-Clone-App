package com.example.translatorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatorapp.R;
import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.models.RecentTranslateModel;

import java.util.ArrayList;
import java.util.List;

public class RecentTranslateAdapter extends RecyclerView.Adapter<RecentTranslateAdapter.ViewHolder> {

    List<RecentSearch> list =new ArrayList<>();

    public RecentTranslateAdapter(List<RecentSearch> list) {
        this.list= list;

    }

    @NonNull
    @Override
    public RecentTranslateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_recyclerview_row_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTranslateAdapter.ViewHolder holder, int position) {
            holder.RecentTranslateFromTextView.setText(list.get(position).getTrsanslationToText());
            holder.RecentTranslateText.setText(list.get(position).getTranslationFromText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RecentTranslateFromTextView,RecentTranslateText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            RecentTranslateFromTextView=itemView.findViewById(R.id.RecentTranslateFromTextView);
            RecentTranslateText=itemView.findViewById(R.id.RecentTranslateText);
        }
    }
}
