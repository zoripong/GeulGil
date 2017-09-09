package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-09-08.
 */

public class RelativeKeyword extends SearchingWord {

    private int id;

    public RelativeKeyword(String word, boolean type, int id) {
        super(word, type);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
