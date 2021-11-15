package com.example.translatorapp.ROOM.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.translatorapp.ROOM.dao.TranslatorDao;
import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;

@Database(entities = {RecentSearch.class, RecentlyTranslatedLanguages.class},version = 3)
public abstract class TranslatorDB extends RoomDatabase {

    public static TranslatorDB instance;

    public abstract TranslatorDao notesDao();

    public static TranslatorDB getInstance(Context context){
        if (instance==null){
            synchronized (TranslatorDB.class){
                if (instance==null){
                    instance= Room.databaseBuilder(context,TranslatorDB.class,"TranslatorDB").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
