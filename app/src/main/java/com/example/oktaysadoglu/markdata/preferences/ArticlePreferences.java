package com.example.oktaysadoglu.markdata.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by oktaysadoglu on 22/12/2016.
 */

public class ArticlePreferences {

    private static final String ARTICLE_TAG = "article";

    public static String getArticle(Context context){

        return PreferenceManager.getDefaultSharedPreferences(context).getString(ARTICLE_TAG,"");

    }

    public static void setArticle(Context context,String article){

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(ARTICLE_TAG,article).apply();

    }
}
