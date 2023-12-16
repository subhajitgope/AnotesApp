package com.subhajit.anotesapp;

import android.content.SharedPreferences;
import android.provider.ContactsContract;

public class Notes {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private  String content;
    public Notes(String title,String content){

    }
}
