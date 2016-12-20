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

    View myView;
    Button sendButton;
    private RecyclerView recyclerView;
    private WordsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_stack,container,false);

        sendButton = (Button) myView.findViewById(R.id.fragment_stack_send_button);

        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

        final MainActivity mainActivity = (MainActivity) getActivity();

        mAdapter = new WordsAdapter(mainActivity.getStackWordsesBundles());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String text = ArticleControllerToSend.markTextWithTag(mainActivity.getStackWordsesBundles());

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.33:8081/addMarkedNews", new Response.Listener<String>() {
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
                        params.put("id", String.valueOf(UTF8.id));
                        params.put("text",text);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);
            }
        });

        return myView;

    }
}
