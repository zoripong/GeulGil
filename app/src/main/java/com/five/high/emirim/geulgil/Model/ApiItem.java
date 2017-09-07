package com.five.high.emirim.geulgil.Model;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-08-17.
 */

// mTitle과 관련된 모든 단어 정보를 가지고 있는 클래스

public class ApiItem {
    private String mTitle;
    private HashSet<WordItem> mWordItems;
    private HashSet<SameSounds> mSameSoundses;

    public ApiItem(){
        this.mTitle = null;
        mWordItems = new HashSet<WordItem>();
        mSameSoundses = new HashSet<SameSounds>();
    }


    public ApiItem(String mTitle, HashSet<WordItem> mWordItems, HashSet<SameSounds> mSameSoundses) {
        this.mTitle = mTitle;
        this.mWordItems = mWordItems;
        this.mSameSoundses = mSameSoundses;
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
