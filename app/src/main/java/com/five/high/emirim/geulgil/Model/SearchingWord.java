package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-09-07.
 */

public class SearchingWord {
    private String word;
    private boolean isMean;

    public SearchingWord(String word, boolean type) {
        this.word = word;
        this.isMean = type;
    }

    public String getWord() {
        return word;
    }

    public boolean isMean() {
        return isMean;
    }
}
