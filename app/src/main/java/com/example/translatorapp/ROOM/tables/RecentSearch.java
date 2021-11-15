package com.example.translatorapp.ROOM.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RecentSearch")
public class RecentSearch {


    @PrimaryKey(autoGenerate = true)
    public int id;
    String TranslateFromLang,TranslateToLang,TranslationFromText,TrsanslationToText,date;


    public RecentSearch(String translateFromLang, String translateToLang, String translationFromText, String trsanslationToText, String date) {
        TranslateFromLang = translateFromLang;
        TranslateToLang = translateToLang;
        TranslationFromText = translationFromText;
        TrsanslationToText = trsanslationToText;
        this.date = date;
    }

    public RecentSearch() {

    }


    public String getTranslateFromLang() {
        return TranslateFromLang;
    }

    public void setTranslateFromLang(String translateFromLang) {
        TranslateFromLang = translateFromLang;
    }

    public String getTranslateToLang() {
        return TranslateToLang;
    }

    public void setTranslateToLang(String translateToLang) {
        TranslateToLang = translateToLang;
    }

    public String getTranslationFromText() {
        return TranslationFromText;
    }

    public void setTranslationFromText(String translationFromText) {
        TranslationFromText = translationFromText;
    }

    public String getTrsanslationToText() {
        return TrsanslationToText;
    }

    public void setTrsanslationToText(String trsanslationToText) {
        TrsanslationToText = trsanslationToText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
