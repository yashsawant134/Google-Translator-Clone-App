package com.example.translatorapp.models;

public class RecentTranslateModel {
    String Translation,TranslateFrom;

    public RecentTranslateModel(String translation, String translateFrom) {
        Translation = translation;
        TranslateFrom = translateFrom;
    }

    public String getTranslation() {
        return Translation;
    }

    public void setTranslation(String translation) {
        Translation = translation;
    }

    public String getTranslateFrom() {
        return TranslateFrom;
    }

    public void setTranslateFrom(String translateFrom) {
        TranslateFrom = translateFrom;
    }
}
