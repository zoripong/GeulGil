package com.five.high.emirim.geulgil.Model;

        import java.io.Serializable;
        import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-10.
 */

public class SameSounds implements Serializable {
    String id;
    ArrayList<WordItem> samesound;
    private boolean isSingle;

    public SameSounds(String id, ArrayList<WordItem> words) {
        this.id = id;
        this.samesound = words;
    }

    public String getId() {
        return id;
    }

    public ArrayList<WordItem> getWordItems() {
        return samesound;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
}

