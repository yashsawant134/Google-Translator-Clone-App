package com.example.translatorapp.ROOM.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;

import java.util.List;

@Dao
public interface TranslatorDao {

    @Insert
    public void Insert(RecentSearch recentSearch);

    @Query("Select * from RecentSearch order by id desc")
    public LiveData<List<RecentSearch>> getRecentSearch();

    @Query("Select * from RecentSearch where TranslateFromLang= :tranlateFromLang and TranslateToLang= :translateToLang order by id desc")
    public LiveData<List<RecentSearch>> getWriteActivityRecentSearch(String tranlateFromLang,String translateToLang);


    //RecentlyTranslatedTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void InsertIntoRecentlyTranslated(RecentlyTranslatedLanguages recentlyTranslatedLanguages);

    @Query("Select * from TranslatedLanguages where fromLang= :fromlang")
    public LiveData<List<RecentlyTranslatedLanguages>> getRecentlyTranslatedLang(String fromlang);

}
