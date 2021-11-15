package com.example.translatorapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;
import com.example.translatorapp.Repository.WriteActivityRepository;

import java.util.List;

public class WriteActivityViewmodel extends AndroidViewModel {

    WriteActivityRepository writeActivityRepository;
    LiveData<List<RecentSearch>> recentSearchList;
    LiveData<List<RecentlyTranslatedLanguages>> recentTranslatedLanguages;

    public WriteActivityViewmodel(@NonNull Application application) {
        super(application);

        writeActivityRepository=new WriteActivityRepository(application);
    }

    public LiveData<List<RecentSearch>> getRecentSearchList(String fromLang , String toLang){
        recentSearchList=writeActivityRepository.getRecentSearchList(fromLang,toLang);
        return recentSearchList;
    }

    public void insertList(RecentSearch recentSearch){
        writeActivityRepository.insertRecentSearch(recentSearch);
    }

    public LiveData<List<RecentSearch>> getAllHistory(){
       return writeActivityRepository.getAllHistory();
    }


    public LiveData<List<RecentlyTranslatedLanguages>> getRecentTranslatedLanguages(String from){
        recentTranslatedLanguages=writeActivityRepository.getRecentlyTranslatedLanguages(from);
        return recentTranslatedLanguages;
    }

    public void insertRecentTranslatedList(RecentlyTranslatedLanguages recentlyTranslatedLanguages){
        writeActivityRepository.insertIntoRecentlyTranslatedLanguages(recentlyTranslatedLanguages);
    }
}
