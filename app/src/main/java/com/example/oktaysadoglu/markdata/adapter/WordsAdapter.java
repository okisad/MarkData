package com.example.oktaysadoglu.markdata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.controller.ClickableController;
import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.models.Word;
import com.example.oktaysadoglu.markdata.preferences.ControlingWordsPreferences;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by oktaysadoglu on 14/12/2016.
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder> {

    List<StackBundle> stackWordses;

    Context context;

    public WordsAdapter() {
    }

    public WordsAdapter(List<StackBundle> stackWordses, Context context) {
        this.stackWordses = stackWordses;
        this.context = context;
    }

    public List<StackBundle> getStackWordses() {
        return stackWordses;
    }

    public void setStackWordses(List<StackBundle> stackWordses) {
        this.stackWordses = stackWordses;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView wordName, wordCat;
        public ImageButton editButton;

        public MyViewHolder(View view) {
            super(view);
            wordName= (TextView) view.findViewById(R.id.word_name);
            wordCat= (TextView) view.findViewById(R.id.word_cat);
            editButton = (ImageButton) view.findViewById(R.id.edit_button);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ClickableController.resetMarkedAndSelectedWords(stackWordses.get(getAdapterPosition()).getStackWords(),context);

                    stackWordses.remove(getAdapterPosition());

                    notifyDataSetChanged();

                }
            });
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StackBundle stackBundle = getStackWordses().get(position);
        holder.wordName.setText(getTextFromStackWords(stackBundle));
        holder.wordCat.setText(getCatFromWord(stackBundle));
    }

    @Override
    public int getItemCount() {
        return getStackWordses().size();
    }

    private String getTextFromStackWords(StackBundle stackBundle){

        Collections.sort(stackBundle.getStackWords(), new Comparator<Word>() {
            @Override
            public int compare(Word word, Word t1) {
                return word.compareTo(t1);
            }
        });

        String text = "";

        for (Word word : stackBundle.getStackWords()){

            text += word.getName()+" ";

        }

        return text.trim();

    }

    private String getCatFromWord(StackBundle stackBundle){

        if (stackBundle.getStackWords().size() > 0){

            Word word = stackBundle.getStackWords().get(0);

            return word.getWordType().toString();

        }else {

            return "";

        }

    }

}
