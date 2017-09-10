package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-09-09.
 */

public class SearchRecordItem {
    private String word;
    private String mean;

    public SearchRecordItem(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }
}
