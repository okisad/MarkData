package com.example.oktaysadoglu.markdata.preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.models.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oktaysadoglu on 22/12/2016.
 */

public class ControllingStackBundlePreferences {

    public static final String STACK_BUNDLE_TAG = "stack_bundle";

    public static List<StackBundle> getStackBundles(Context context){

        Gson gson = new Gson();

        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(STACK_BUNDLE_TAG,null);

        List<StackBundle> stackBundles = gson.fromJson(json,new TypeToken<ArrayList<StackBundle>>() {}.getType());

        return stackBundles;
    }

    public static void setStackBundles(Context context,List<StackBundle> stackBundles){

        Gson gson = new Gson();

        String json = gson.toJson(stackBundles);

        Log.e("stackJsonset",json);

        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(STACK_BUNDLE_TAG,json).apply();

    }


}
