package com.example.translatorapp.ROOM.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TranslatedLanguages")
public class RecentlyTranslatedLanguages {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String fromLang,toLang;

    public RecentlyTranslatedLanguages(String fromLang, String toLang) {
        this.fromLang = fromLang;
        this.toLang = toLang;
    }

    public String getFromLang() {
        return fromLang;
    }

    public void setFromLang(String fromLang) {
        this.fromLang = fromLang;
    }

    public String getToLang() {
        return toLang;
    }

    public void setToLang(String toLang) {
        this.toLang = toLang;
    }
}
