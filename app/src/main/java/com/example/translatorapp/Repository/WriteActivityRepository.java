package com.example.translatorapp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.translatorapp.ROOM.dao.TranslatorDao;
import com.example.translatorapp.ROOM.database.TranslatorDB;
import com.example.translatorapp.ROOM.tables.RecentSearch;
import com.example.translatorapp.ROOM.tables.RecentlyTranslatedLanguages;

import java.util.List;

public class WriteActivityRepository {

    LiveData<List<RecentSearch>> recentSearchList;
//    LiveData<List<RecentSearch>> allrecentSearch;
    LiveData<List<RecentlyTranslatedLanguages>> recentlyTranslatedLanguages;
    public TranslatorDao translatorDao;

    public WriteActivityRepository(Application application) {
        TranslatorDB translatorDB=TranslatorDB.getInstance(application);
        translatorDao=translatorDB.notesDao();


    }

    public void insertRecentSearch(RecentSearch recentSearch){
        new InsertAsyncTask(translatorDao).execute(recentSearch);
    }


    public LiveData<List<RecentSearch>> getRecentSearchList(String fromLang , String toLang){
        recentSearchList=translatorDao.getWriteActivityRecentSearch(fromLang,toLang);
        return recentSearchList;
    }

//    public LiveData<List<RecentSearch>> getallRecentSearchList(String fromLang , String toLang){
//        allrecentSearchList=translatorDao.getWriteActivityRecentSearch(fromLang,toLang);
//        return recentSearchList;
//    }

    public LiveData<List<RecentSearch>> getAllHistory(){
        return translatorDao.getRecentSearch();
    }

    public static class InsertAsyncTask extends AsyncTask<RecentSearch,Void,Void>{
        public TranslatorDao translatorDao;
        public InsertAsyncTask(TranslatorDao translatorDao){
            this.translatorDao=translatorDao;
        }
        @Override
        protected Void doInBackground(RecentSearch... recentSearches) {
            translatorDao.Insert(recentSearches[0]);
            return null;
        }
    }









    public void insertIntoRecentlyTranslatedLanguages(RecentlyTranslatedLanguages recentlyTranslatedLanguages){
        new InsertAsyncTaskRecentlyTranslatedLanguage(translatorDao).execute(recentlyTranslatedLanguages);
    }

    public LiveData<List<RecentlyTranslatedLanguages>> getRecentlyTranslatedLanguages(String from){
        recentlyTranslatedLanguages=translatorDao.getRecentlyTranslatedLang(from);
        return recentlyTranslatedLanguages;
    }

    private class InsertAsyncTaskRecentlyTranslatedLanguage extends AsyncTask<RecentlyTranslatedLanguages,Void,Void>{
        TranslatorDao translatorDao;
        public InsertAsyncTaskRecentlyTranslatedLanguage(TranslatorDao translatorDao){
            this.translatorDao=translatorDao;
        }

        @Override
        protected Void doInBackground(RecentlyTranslatedLanguages... recentlyTranslatedLanguages) {
            translatorDao.InsertIntoRecentlyTranslated(recentlyTranslatedLanguages[0]);
            return null;
        }
    }
}
