package com.five.high.emirim.geulgil.Model;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-08-17.
 */

// mTitle과 관련된 모든 단어 정보를 가지고 있는 클래스

public class ApiItem {
    private String title;
    private HashSet<SameSounds> relatives;

    public ApiItem(String title, HashSet<SameSounds> relatives) {
        this.title = title;
        this.relatives = relatives;
    }

    public String getTitle() {
        return title;
    }

    public HashSet<SameSounds> getRelatives() {
        return relatives;
    }

    @Override
    public String toString() {
        return "ApiItem{" +
                "title='" + title + '\'' +
                ", relatives=" +
                '}';
    }

}
