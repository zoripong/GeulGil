package com.five.high.emirim.geulgil.Model;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-08-17.
 */

// mTitle과 관련된 모든 단어 정보를 가지고 있는 클래스

public class ApiItem {
    private String title;
    private HashSet<WordItem> relatives;
    private HashSet<SameSounds> sameSoundses;

    public ApiItem(String title, HashSet<WordItem> relatives, HashSet<SameSounds> sameSoundses) {
        this.title = title;
        this.relatives = relatives;
        this.sameSoundses = sameSoundses;
    }

    public String getTitle() {
        return title;
    }

    public HashSet<WordItem> getRelatives() {
        return relatives;
    }

    public HashSet<SameSounds> getSameSoundses() {
        return sameSoundses;
    }
}
