package com.example.oktaysadoglu.markdata.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by oktaysadoglu on 22/12/2016.
 */

public class TextSizePreferences {

    private static final String TEXT_SIZE = "text_size";

    public static int getTextSize(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(TEXT_SIZE,20);
    }

    public static void setTextSize(Context context,int textSize){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(TEXT_SIZE,textSize).apply();
    }

}
