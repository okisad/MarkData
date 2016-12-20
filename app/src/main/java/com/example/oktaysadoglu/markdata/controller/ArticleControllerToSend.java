package com.example.oktaysadoglu.markdata.controller;

import android.util.Log;

import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.models.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by oktaysadoglu on 19/12/2016.
 */

public class ArticleControllerToSend {

    public static String text;

    public static Map<Integer, Integer> positionMap = new HashMap<>();

    public static String markTextWithTag(List<StackBundle> stackBundles) {

        StringBuilder stringBuilder = new StringBuilder(text);

        String text = "";

        positionMap.clear();

        for (StackBundle stackBundle : stackBundles) {

            Word previousWord = null;

            int i = 1;

            for (Word word : stackBundle.getStackWords()) {

                String startingTag = "<" + word.getWordType().toString() + ">";

                String endingTag = "</" + word.getWordType().toString() + ">";

                int count = 0;

                if (previousWord != null) {

                    if (stackBundle.getStackWords().size() == i) {

                        if (word.getStartPlace() - previousWord.getEndPlace() < 2) {

                            text = stringBuilder.insert(word.getEndPlace() + getCount(word,true), endingTag).toString();

                            positionMap.put(word.getEndPlace(), endingTag.length());

                        } else {

                            text = stringBuilder.insert(previousWord.getEndPlace() + getCount(previousWord,true), endingTag).toString();

                            positionMap.put(previousWord.getEndPlace(), endingTag.length());

                            if (stackBundle.getStackWords().size() == i) {

                                stringBuilder.insert(word.getStartPlace() + getCount(word,false), startingTag).toString();

                                positionMap.put(word.getStartPlace(), startingTag.length());

                                text = stringBuilder.insert(word.getEndPlace() + getCount(word,true), endingTag).toString();

                                positionMap.put(word.getEndPlace(), endingTag.length());

                            }

                            continue;

                        }

                    } else {

                        if (word.getStartPlace() - previousWord.getEndPlace() < 2) {

                            previousWord = word;

                            i++;

                            continue;

                        }else {

                            stringBuilder.insert(previousWord.getEndPlace() + getCount(previousWord,true), endingTag).toString();

                            positionMap.put(previousWord.getEndPlace(), endingTag.length());

                            text = stringBuilder.insert(word.getStartPlace()+getCount(word,false),startingTag).toString();

                            positionMap.put(word.getStartPlace(),startingTag.length());


                        }

                    }

                } else {

                    text = stringBuilder.insert(word.getStartPlace() + getCount(word,false), startingTag).toString();

                    positionMap.put(word.getStartPlace(), startingTag.length());

                }

                previousWord = word;

                i++;

            }

        }

        Log.e("my", text);

        return text;
    }

    private static Map<Integer, Integer> orderMap(Map<Integer, Integer> positionMap) {

        Map<Integer, Integer> sortedMap = new TreeMap<>(positionMap);

        printMap(sortedMap);

        return sortedMap;

    }

    private static int getCount(Word word,boolean analyzePunc) {

        int count = 0;

        for (Map.Entry<Integer, Integer> entry : positionMap.entrySet()) {

            if (word.getStartPlace() >= entry.getKey()) {

                count = count + entry.getValue();

            }

        }

        if (analyzePunc){

            if (String.valueOf(text.charAt(word.getEndPlace()-1)).matches(".*\\p{Punct}")){

                count--;

            }

        }

        return count;

    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            Log.e("my", "Key : " + entry.getKey() + " Value : " + entry.getValue());
        }
    }

    public static String getText() {
        return text;
    }

    public static void setText(String text) {
        ArticleControllerToSend.text = text;
    }
}
