package com.five.high.emirim.geulgil.Model;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-09-10.
 */

public class SameSounds {
    String id;
    HashSet<WordItem> words;

    public SameSounds(String id, HashSet<WordItem> words) {
        this.id = id;
        this.words = words;
    }

    public String getId() {
        return id;
    }

    public HashSet<WordItem> getWords() {
        return words;
    }

}
