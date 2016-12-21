package com.example.oktaysadoglu.markdata.controller;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.models.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oktaysadoglu on 14/12/2016.
 */

public class ClickableController {

    private int selectedCount = 0;

    public static List<Word> words = new ArrayList<>();

    private Activity activity;

    public ClickableController(Activity activity) {

        setActivity(activity);

    }

    public void getEachWord(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices(
                textView.getText().toString().trim(), ' ');

        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
            // to cater last/only word
            end = (i <indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_COMPOSING);
            start = end + 1;
        }
    }

    private ClickableSpan getClickableSpan(){

        return new ClickableSpan() {

            StyleSpan boldSpan = new StyleSpan( Typeface.BOLD);

            @Override
            public void onClick(View widget) {

                boolean isContain = false;

                TextView tv = (TextView) widget;

                Spannable spannable = (Spannable) tv.getText();

                String s = tv
                        .getText()
                        .subSequence(tv.getSelectionStart(),
                                tv.getSelectionEnd()).toString();

                Word word = new Word();

                if (words.size() > 0){

                    for (Word word1:words){

                        if(word1.getStartPlace()==tv.getSelectionStart()){

                            isContain = true;
                            word = word1;

                            if (word1.isMarked())
                                return;

                        }

                    }

                }

                if (!isContain){

                    word.setStartPlace(tv.getSelectionStart());
                    word.setEndPlace(tv.getSelectionEnd());
                    word.setName(s);
                    word.setSelected(true);
                    words.add(word);

                }else {
                    word.setSelected(!word.isSelected());
                }



                if (s.matches(".*\\p{Punct}")){
                    ArrayList<Integer> positions = new ArrayList();
                    Pattern p = Pattern.compile(".*\\p{Punct}");  // insert your pattern here
                    Matcher m = p.matcher(s);
                    while (m.find()) {
                        s = tv.getText().subSequence(tv.getSelectionStart(),tv.getSelectionEnd()-1).toString();
                        spannable.setSpan( boldSpan, tv.getSelectionStart(), tv.getSelectionEnd()-1, Spannable.SPAN_PRIORITY );
                        word.setName(s);
                    }
                }else {
                    spannable.setSpan( boldSpan, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_PRIORITY );
                }

                if (word.isSelected()){
                    selectedCount++;
                }else {
                    selectedCount--;
                    spannable.removeSpan(boldSpan);
                    StyleSpan normalSpan= new StyleSpan( Typeface.NORMAL);
                    spannable.setSpan( normalSpan, tv.getSelectionStart(), tv.getSelectionEnd(), Spannable.SPAN_PRIORITY);
                }

                if (selectedCount > 0 ){

                    AHBottomNavigation bottomNavigation = (AHBottomNavigation) activity.findViewById(R.id.bottom_navigation);
                    bottomNavigation.setVisibility(View.VISIBLE);
                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);
                }else {
                    AHBottomNavigation bottomNavigation = (AHBottomNavigation) activity.findViewById(R.id.bottom_navigation);
                    bottomNavigation.setVisibility(View.GONE);
                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);
                }

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
    }

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public static void resetMarkedAndSelectedWords(List<Word> stackedWords){

        for (Word word :words){

            for (Word stackedWord:stackedWords){

                if (word.getStartPlace() == stackedWord.getStartPlace()){

                    word.setMarked(false);

                    word.setSelected(false);

                }

            }

        }


    }

    public static void resetSelectedWords(){

        for (Word word :words){

            word.setSelected(false);

        }

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static List<Word> getWords() {
        return words;
    }

    public static void setWords(List<Word> words) {
        ClickableController.words = words;
    }
}
