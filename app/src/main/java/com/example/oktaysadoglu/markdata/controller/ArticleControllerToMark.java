package com.example.oktaysadoglu.markdata.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.activities.MainActivity;
import com.example.oktaysadoglu.markdata.enums.WordType;
import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.models.Word;

import java.util.List;
import java.util.Stack;

/**
 * Created by oktaysadoglu on 20/12/2016.
 */

public class ArticleControllerToMark {

    private View textFragmentView;

    private AHBottomNavigation bottomNavigation;

    private TextView mainTextView;

    private MainActivity mainActivity;

    private ClickableController clickableController;

    public ArticleControllerToMark(View textFragmentView,MainActivity mainActivity, ClickableController clickableController) {
        setTextFragmentView(textFragmentView);
        setBottomNavigation((AHBottomNavigation) getTextFragmentView().findViewById(R.id.bottom_navigation));
        setMainTextView((TextView) getTextFragmentView().findViewById(R.id.my_text));
        setMainActivity(mainActivity);
        setClickableController(clickableController);
    }

    public void setBottomBar() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Kisi", R.drawable.ic_bottom);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Kurum", R.drawable.ic_bottom);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Yer", R.drawable.ic_bottom);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Tarih", R.drawable.ic_bottom);

        getBottomNavigation().addItem(item1);
        getBottomNavigation().addItem(item2);
        getBottomNavigation().addItem(item3);
        getBottomNavigation().addItem(item4);

        getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

        getBottomNavigation().setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        getBottomNavigation().setBehaviorTranslationEnabled(false);

        getBottomNavigation().setForceTint(true);

        getBottomNavigation().setTranslucentNavigationEnabled(true);

        getBottomNavigation().setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        getBottomNavigation().setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (position == 0) {

                    getMainActivity().getStackWordsesBundles().add(markWords(WordType.PERSON));

                    paintWords();

                    getBottomNavigation().setVisibility(View.GONE);

                    getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    getClickableController().setSelectedCount(0);

                } else if (position == 1) {

                    getMainActivity().getStackWordsesBundles().add(markWords(WordType.INSTITUTION));

                    paintWords();

                    getBottomNavigation().setVisibility(View.GONE);

                    getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    getClickableController().setSelectedCount(0);

                } else if (position == 2) {

                    getMainActivity().getStackWordsesBundles().add(markWords(WordType.PLACE));

                    paintWords();

                    getBottomNavigation().setVisibility(View.GONE);

                    getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    getClickableController().setSelectedCount(0);

                } else if (position == 3) {

                    getMainActivity().getStackWordsesBundles().add(markWords(WordType.DATE));

                    paintWords();

                    getBottomNavigation().setVisibility(View.GONE);

                    getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    getClickableController().setSelectedCount(0);

                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    public StackBundle markWords(WordType wordType){

        StackBundle stackBundle = new StackBundle();

        List<Word> words = ClickableController.words;

        for (Word word : words) {

            if (word.isSelected()) {

                word.setMarked(true);

                word.setSelected(false);

                word.setWordType(wordType);

                stackBundle.getStackWords().add(word);

            }

        }

        return stackBundle;

    }
    public void paintWords(){

        for (Word word : ClickableController.words) {

            if (word.isMarked()) {

                Spannable spannable = (Spannable) getMainTextView().getText();

                if (word.getWordType() == WordType.DATE) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getMainActivity(), R.color.colorTarih)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.PERSON) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getMainActivity(), R.color.colorKisi)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.INSTITUTION) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getMainActivity(), R.color.colorKurum)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.PLACE) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getMainActivity(), R.color.colorYer)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                getMainTextView().setText(spannable);

            }

        }
    }

    public void goneBottomNavigation(){

        getBottomNavigation().setVisibility(View.GONE);

        getBottomNavigation().setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public View getTextFragmentView() {
        return textFragmentView;
    }

    public void setTextFragmentView(View textFragmentView) {
        this.textFragmentView = textFragmentView;
    }

    public AHBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }

    public void setBottomNavigation(AHBottomNavigation bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

    public TextView getMainTextView() {
        return mainTextView;
    }

    public void setMainTextView(TextView mainTextView) {
        this.mainTextView = mainTextView;
    }

    public ClickableController getClickableController() {
        return clickableController;
    }

    public void setClickableController(ClickableController clickableController) {
        this.clickableController = clickableController;
    }
}
