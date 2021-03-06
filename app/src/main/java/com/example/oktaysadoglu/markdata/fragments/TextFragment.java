package com.example.oktaysadoglu.markdata.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
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
import com.example.oktaysadoglu.markdata.controller.ArticleControllerToMark;
import com.example.oktaysadoglu.markdata.controller.ArticleControllerToSend;
import com.example.oktaysadoglu.markdata.controller.ClickableController;
import com.example.oktaysadoglu.markdata.activities.MainActivity;
import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.spannable.UTF8;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oktaysadoglu on 14/12/2016.
 */

public class TextFragment extends Fragment {

    private View textFragmentView;

    private TextView mainTextView,noConTextView ;

    private ScrollView scrollView;

    private Button refreshButton;

    private MainActivity mainActivity;

    private ArticleControllerToMark articleControllerToMark;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMainActivity((MainActivity)getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setTextFragmentView(inflater.inflate(R.layout.fragment_text, container, false));

        bindViews();

        setArticleControllerToMark(new ArticleControllerToMark(getTextFragmentView(),getMainActivity(),getMainActivity().getClickableController()));

        getArticleControllerToMark().setBottomBar();

        return getTextFragmentView();
    }

    private void bindViews(){

        setMainTextView((TextView) textFragmentView.findViewById(R.id.my_text));

        getMainTextView().setTextSize(MainActivity.news_text_size);

        setScrollView((ScrollView) textFragmentView.findViewById(R.id.scrollView));

        setNoConTextView((TextView) textFragmentView.findViewById(R.id.text_fragment_no_connection_text));

        setRefreshButton((Button) textFragmentView.findViewById(R.id.text_fragment_no_connection_refresh_button));

    }

    @Override
    public void onStart() {
        super.onStart();

        fetchDataFromUrl();

        getArticleControllerToMark().paintWords();

    }

    @Override
    public void onPause() {
        super.onPause();

        ClickableController.resetSelectedWords();

        getArticleControllerToMark().goneBottomNavigation();

    }

    private void fetchDataFromUrl() {


        if (MainActivity.getArticle() == null)

            if (MainActivity.isConnected(getContext())) {

                adjustScreenForInternet(true);

                requestText();

            } else {

                adjustScreenForInternet(false);

                setRefreshButtonListener();

            }
        else
            writeText();

    }

    private void setRefreshButtonListener() {

        getScrollView().setVisibility(View.GONE);

        getRefreshButton().setVisibility(View.VISIBLE);

        getRefreshButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromUrl();

                getArticleControllerToMark().paintWords();
            }
        });

    }

    private void requestText() {

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());

        UTF8 utf8 = new UTF8(Request.Method.GET, getString(R.string.get_url_for_unmarked_news), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    MainActivity.setArticle(response.get(getString(R.string.text_parameter_to_get_unmarked_news)).toString());

                    UTF8.id = response.getInt(getString(R.string.id_parameter_to_get_unmarked_news));

                    getMainTextView().setText(MainActivity.getArticle(), TextView.BufferType.SPANNABLE);

                    ArticleControllerToSend.setText(MainActivity.getArticle());

                    getMainActivity().getClickableController().getEachWord(getMainTextView());

                    getMainTextView().setHighlightColor(Color.TRANSPARENT);

                    getMainTextView().setMovementMethod(LinkMovementMethod.getInstance());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getMainTextView().setText(getString(R.string.error_server));
            }
        });

        requestQueue.add(utf8);

    }

    private void writeText() {

        adjustScreenForInternet(true);

        getMainTextView().setText(MainActivity.getArticle(), TextView.BufferType.SPANNABLE);

        getMainActivity().getClickableController().getEachWord(getMainTextView());

        getMainTextView().setHighlightColor(Color.TRANSPARENT);

        getMainTextView().setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void adjustScreenForInternet(boolean isAvailableInternet){

        if (isAvailableInternet){

            getNoConTextView().setVisibility(View.GONE);

            getRefreshButton().setVisibility(View.GONE);

            getScrollView().setVisibility(View.VISIBLE);

        }else {

            getRefreshButton().setVisibility(View.VISIBLE);

            getNoConTextView().setVisibility(View.VISIBLE);

            getScrollView().setVisibility(View.GONE);

        }

    }

    public View getTextFragmentView() {
        return textFragmentView;
    }

    public void setTextFragmentView(View textFragmentView) {
        this.textFragmentView = textFragmentView;
    }

    public TextView getMainTextView() {
        return mainTextView;
    }

    public void setMainTextView(TextView mainTextView) {
        this.mainTextView = mainTextView;
    }

    public TextView getNoConTextView() {
        return noConTextView;
    }

    public void setNoConTextView(TextView noConTextView) {
        this.noConTextView = noConTextView;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(Button refreshButton) {
        this.refreshButton = refreshButton;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ArticleControllerToMark getArticleControllerToMark() {
        return articleControllerToMark;
    }

    public void setArticleControllerToMark(ArticleControllerToMark articleControllerToMark) {
        this.articleControllerToMark = articleControllerToMark;
    }
}
