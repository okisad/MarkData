package com.example.oktaysadoglu.markdata.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oktaysadoglu on 16/12/2016.
 */

public class StackBundle {

    private List<Word> stackWords = new ArrayList<>();

    public StackBundle(List<Word> stackWords) {
        this.stackWords = stackWords;
    }

    public StackBundle() {
    }

    public List<Word> getStackWords() {
        return stackWords;
    }

    public void setStackWords(List<Word> stackWords) {
        this.stackWords = stackWords;
    }
}
