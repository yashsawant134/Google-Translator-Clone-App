package com.example.translatorapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.adapters.RecentTranslateAdapter;
import com.example.translatorapp.viewmodel.WriteActivityViewmodel;

import java.util.List;

public class HistoryFragment extends Fragment {
    WriteActivityViewmodel writeActivityViewmodel;
    RecyclerView HistoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history, container, false);
        HistoryRecyclerView=view.findViewById(R.id.HistoryRecyclerView);

        writeActivityViewmodel=new ViewModelProvider(this).get(WriteActivityViewmodel.class);
        writeActivityViewmodel.getAllHistory().observe(getActivity(), new Observer<List<RecentSearch>>() {
            @Override
            public void onChanged(List<RecentSearch> recentSearches) {
                RecentTranslateAdapter recentTranslateAdapter=new RecentTranslateAdapter(recentSearches);
                HistoryRecyclerView.setAdapter(recentTranslateAdapter);

            }
        });
        return view;
    }
}