package com.five.high.emirim.geulgil.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-10.
 */

public class SameSounds implements Serializable {
    String id;
    ArrayList<WordItem> words;
    private boolean isSingle;

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

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
}
