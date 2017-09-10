package com.five.high.emirim.geulgil.Model;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-10.
 */

public class SameSounds {
    String id;
    ArrayList<WordItem> words;

    public SameSounds(String id, ArrayList<WordItem> words) {
        this.id = id;
        this.words = words;
    }

    public String getId() {
        return id;
    }

    public ArrayList<WordItem> getWords() {
        return words;
    }
}
