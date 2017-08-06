package com.five.high.emirim.geulgil.Model;

import java.util.ArrayList;

/**
 * Created by 두리 on 2017-07-26.
 */

public class MeanApiItem {
    public String word;
    public ArrayList<Sense> senseList;

    public MeanApiItem() {
        senseList=new ArrayList<Sense>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public MeanApiItem(String word, ArrayList<Sense> senseList) {
        this.word = word;
        this.senseList = senseList;
    }
}
