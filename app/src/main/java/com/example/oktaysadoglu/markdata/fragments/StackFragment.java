package com.example.oktaysadoglu.markdata.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oktaysadoglu.markdata.activities.MainActivity;
import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.adapter.WordsAdapter;
import com.example.oktaysadoglu.markdata.controller.ArticleControllerToSend;
import com.example.oktaysadoglu.markdata.spannable.UTF8;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oktaysadoglu on 14/12/2016.
 */

public class StackFragment extends Fragment{

    private View stackFragmentView;
    private Button sendButton;
    private RecyclerView recyclerView;
    private WordsAdapter mAdapter;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setStackFragmentView(inflater.inflate(R.layout.fragment_stack,container,false));

        bindViews();

        setListItems();

        setSendButtonOnClickListener();

        return getStackFragmentView();

    }

    private void bindViews(){

        setSendButton((Button) getStackFragmentView().findViewById(R.id.fragment_stack_send_button));

        setRecyclerView((RecyclerView) getStackFragmentView().findViewById(R.id.recycler_view));

        setMainActivity((MainActivity) getActivity());


    }

    private void setListItems(){

        setmAdapter(new WordsAdapter(getMainActivity().getStackWordsesBundles()));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        getRecyclerView().setLayoutManager(mLayoutManager);
        getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        getRecyclerView().setAdapter(getmAdapter());

    }

    private void setSendButtonOnClickListener(){

        getSendButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String text = ArticleControllerToSend.markTextWithTag(getMainActivity().getStackWordsesBundles());

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.send_url_for_marked_news), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("my",error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(getString(R.string.id_parameter_to_send_marked_news), String.valueOf(UTF8.id));
                        params.put(getString(R.string.text_parameter_to_send_marked_news),text);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);
            }
        });

    }

    public View getStackFragmentView() {
        return stackFragmentView;
    }

    public void setStackFragmentView(View stackFragmentView) {
        this.stackFragmentView = stackFragmentView;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void setSendButton(Button sendButton) {
        this.sendButton = sendButton;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public WordsAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(WordsAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
