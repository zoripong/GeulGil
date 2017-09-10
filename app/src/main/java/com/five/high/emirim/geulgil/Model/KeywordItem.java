package com.five.high.emirim.geulgil.Model;

import java.io.Serializable;

/**
 * Created by 유리 on 2017-09-07.
 */

public class KeywordItem implements Serializable {
    private String word;
    private boolean isMean;


    public KeywordItem(String word, boolean type) {
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
