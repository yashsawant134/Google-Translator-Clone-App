package com.example.translatorapp.models;

public class Languagesmodel {

    String lang;
    String from;
    Boolean isSelected;
    String code;

    public Languagesmodel(String lang, String from, Boolean isSelected, String code) {
        this.lang = lang;
        this.from = from;
        this.isSelected = isSelected;
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
