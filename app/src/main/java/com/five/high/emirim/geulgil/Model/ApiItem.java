package com.five.high.emirim.geulgil.Model;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-08-17.
 */

public class ApiItem {
    private String mTitle;
    private HashSet<WordItem> mWordItems;

    public ApiItem(){
        this.mTitle = null;
        mWordItems = new HashSet<WordItem>();
    }

    public ApiItem(String mTitle, HashSet<WordItem> mWordItems) {
        this.mTitle = mTitle;
        this.mWordItems = mWordItems;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public HashSet<WordItem> getmWordItems() {
        return mWordItems;
    }

    public void setmWordItems(HashSet<WordItem> mWordItems) {
        this.mWordItems = mWordItems;
    }
}
