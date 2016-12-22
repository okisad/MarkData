package com.example.oktaysadoglu.markdata.preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.oktaysadoglu.markdata.models.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oktaysadoglu on 22/12/2016.
 */

public class ControlingWordsPreferences {

    private static final String WORDS = "words";

    public static List<Word> getWords(Context context){

        Gson gson = new Gson();

        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(WORDS,null);

        List<Word> words = gson.fromJson(json, new TypeToken<ArrayList<Word>>() {}.getType());

        if (words != null)
            Log.e("jsone",words.toString());

        return words;

    }

    public static void setWords(Context context,List<Word> words){

        Gson gson = new Gson();

        String json = gson.toJson(words);

        Log.e("json",json);

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(WORDS,json).apply();

    }



}
