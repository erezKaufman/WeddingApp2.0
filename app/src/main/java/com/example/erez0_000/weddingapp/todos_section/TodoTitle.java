package com.example.erez0_000.weddingapp.todos_section;

import android.content.Context;
import android.widget.CheckBox;

public class TodoTitle {
    private String title;
    private Context mContext;
    public TodoTitle(String groupName, Context context){
        title = groupName;
        mContext = context;
    }

    public String getTitle() {
        return title;
    }

    public Context getmContext() {
        return mContext;
    }
}
