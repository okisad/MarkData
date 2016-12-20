package com.example.oktaysadoglu.markdata.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.oktaysadoglu.markdata.controller.ArticleController;
import com.example.oktaysadoglu.markdata.controller.ClickableController;
import com.example.oktaysadoglu.markdata.activities.MainActivity;
import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.enums.WordType;
import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.spannable.UTF8;
import com.example.oktaysadoglu.markdata.models.Word;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oktaysadoglu on 14/12/2016.
 */

public class TextFragment extends Fragment {

    View myView;

    ClickableController clickableController;

    AHBottomNavigation bottomNavigation;

    TextView mainTextView,noConTextView ;

    ScrollView scrollView;

    Button refreshButton;

    StackBundle stackBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();

        clickableController = mainActivity.getClickableController();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_text, container, false);

        bindViews();

        setBottomBar();

        return myView;
    }

    private void bindViews(){

        bottomNavigation = (AHBottomNavigation) myView.findViewById(R.id.bottom_navigation);

        mainTextView = (TextView) myView.findViewById(R.id.my_text);

        scrollView = (ScrollView) myView.findViewById(R.id.scrollView);

        noConTextView = (TextView) myView.findViewById(R.id.text_fragment_no_connection_text);

        refreshButton = (Button) myView.findViewById(R.id.text_fragment_no_connection_refresh_button);

    }

    @Override
    public void onStart() {
        super.onStart();

        fetchDataFromUrl();

        markWordsBeforeWritten();
    }

    @Override
    public void onPause() {
        super.onPause();

        ClickableController.resetSelectedWords();

        bottomNavigation.setVisibility(View.GONE);

        bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

    }

    private void fetchDataFromUrl() {


        if (MainActivity.getArticle() == null)

            if (MainActivity.isConnected(getContext())) {

                noConTextView.setVisibility(View.GONE);

                refreshButton.setVisibility(View.GONE);

                scrollView.setVisibility(View.VISIBLE);

                requestText();

            } else {

                refreshButton.setVisibility(View.VISIBLE);

                noConTextView.setVisibility(View.VISIBLE);

                scrollView.setVisibility(View.GONE);

                setRefreshButtonListener();

            }
        else
            writeText();

    }

    private void setRefreshButtonListener() {

        scrollView.setVisibility(View.GONE);

        refreshButton.setVisibility(View.VISIBLE);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromUrl();

                markWordsBeforeWritten();
            }
        });

    }

    private void setBottomBar() {

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Kisi", R.drawable.ic_bottom);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Kurum", R.drawable.ic_bottom);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Yer", R.drawable.ic_bottom);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Tarih", R.drawable.ic_bottom);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);


// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Change colors
        /*bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));*/

// Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

// Display color under navigation bar (API 21+)
// Don't forget these lines in your style-v21
// <item name="android:windowTranslucentNavigation">true</item>
// <item name="android:fitsSystemWindows">true</item>
        bottomNavigation.setTranslucentNavigationEnabled(true);

// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        /*bottomNavigation.setColored(true);*/


// Customize notification (title, background, typeface)
        /*bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));*/


// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (position == 0) {

                    markWords(WordType.PERSON);

                    colorMarkedWord();

                    bottomNavigation.setVisibility(View.GONE);

                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    clickableController.setSelectedCount(0);

                } else if (position == 1) {

                    markWords(WordType.INSTITUTION);

                    colorMarkedWord();

                    bottomNavigation.setVisibility(View.GONE);

                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    clickableController.setSelectedCount(0);

                } else if (position == 2) {

                    markWords(WordType.PLACE);

                    colorMarkedWord();

                    bottomNavigation.setVisibility(View.GONE);

                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    clickableController.setSelectedCount(0);

                } else if (position == 3) {

                    markWords(WordType.DATE);

                    colorMarkedWord();

                    bottomNavigation.setVisibility(View.GONE);

                    bottomNavigation.setCurrentItem(AHBottomNavigation.CURRENT_ITEM_NONE);

                    clickableController.setSelectedCount(0);

                }

                // Do something cool here...
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

    private void markWords(WordType wordType) {

        stackBundle = new StackBundle();

        List<Word> words = ClickableController.words;

        for (Word word : words) {

            if (word.isSelected()) {

                word.setMarked(true);

                word.setSelected(false);

                word.setWordType(wordType);

                stackBundle.getStackWords().add(word);

            }

        }

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.getStackWordses().add(stackBundle);
    }

    private void colorMarkedWord() {

        List<Word> words = ClickableController.words;

        paintWords(words);


    }

    private void markWordsBeforeWritten() {

        List<Word> words = ClickableController.words;

        paintWords(words);

    }

    private void paintWords(List<Word> words) {

        for (Word word : words) {

            if (word.isMarked()) {

                Spannable spannable = (Spannable) mainTextView.getText();

                if (word.getWordType() == WordType.DATE) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorTarih)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.PERSON) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorKisi)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.INSTITUTION) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorKurum)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                } else if (word.getWordType() == WordType.PLACE) {

                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorYer)), word.getStartPlace(), word.getEndPlace(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }

                mainTextView.setText(spannable);

            }

        }


    }


    private void requestText() {

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());

        UTF8 utf8 = new UTF8(Request.Method.GET, "http://192.168.1.33:8081/getNewspaper", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    MainActivity.setArticle(response.get("text").toString());

                    UTF8.id = response.getInt("_id");

                    mainTextView.setText(MainActivity.getArticle(), TextView.BufferType.SPANNABLE);

                    ArticleController.setText(MainActivity.getArticle());

                    clickableController.getEachWord(mainTextView);

                    mainTextView.setHighlightColor(Color.TRANSPARENT);

                    mainTextView.setMovementMethod(LinkMovementMethod.getInstance());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainTextView.setText("Sunucu dan kaynaklı hata");
            }
        });

        requestQueue.add(utf8);

    }

    private void writeText() {

        noConTextView.setVisibility(View.GONE);

        refreshButton.setVisibility(View.GONE);

        mainTextView.setText(MainActivity.getArticle(), TextView.BufferType.SPANNABLE);

        clickableController.getEachWord(mainTextView);

        mainTextView.setHighlightColor(Color.TRANSPARENT);

        mainTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
