package com.example.oktaysadoglu.markdata.models;

import com.example.oktaysadoglu.markdata.enums.WordType;

/**
 * Created by oktaysadoglu on 24/11/2016.
 */

public class Word implements Comparable{

    private String name;
    private int startPlace;
    private int endPlace;
    private boolean selected;
    private boolean marked;
    private WordType wordType;

    public Word(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
        setWordType(WordType.PLACE);
    }

    public Word(String name) {
        this.name = name;
        setSelected(false);
        setWordType(WordType.PLACE);
    }

    public Word() {
        setSelected(false);
        setWordType(WordType.PLACE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public int getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(int endPlace) {
        this.endPlace = endPlace;
    }

    public int getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(int startPlace) {
        this.startPlace = startPlace;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public String toString() {
        return this.name +", "+this.isSelected()+"\n";
    }

    @Override
    public int compareTo(Object o) {
        int comper = ((Word) o).getStartPlace();

        return getStartPlace()-comper;
    }
}
