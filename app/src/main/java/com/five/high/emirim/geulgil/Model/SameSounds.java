package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-09-06.
 */

public class SameSounds {
    private String word;
    private int ids[];

    public String getWord() {
        return word;
    }

    public int[] getIds() {
        return ids;
    }

    public SameSounds(String word, int[] ids) {
        this.word = word;
        this.ids = ids;
    }
}
