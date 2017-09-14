package com.five.high.emirim.geulgil.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 유리 on 2017-06-16.
 */

public class WordItem implements Serializable{
    private int id;
    private String word;
    private String mean;
    private String part;
    private ArrayList<String> meankeyword;
    private ArrayList<String> similarkeyword;
    private int recommend;

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    public String getPart() {
        return part;
    }

    public ArrayList<String> getMeankeyword() {
        return meankeyword;
    }

    public ArrayList<String> getSimilarkeyword() {
        return similarkeyword;
    }

    public int getRecommend() {
        return recommend;
    }

    public WordItem(int id, String word, String mean, String part, ArrayList<String> meankeyword, ArrayList<String> similarkeyword, int recommend) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.part = part;
        this.meankeyword = meankeyword;
        this.similarkeyword = similarkeyword;
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "WordItem{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", mean='" + mean + '\'' +
                ", part='" + part + '\'' +
                ", meankeyword=" + meankeyword +
                ", similarkeyword=" + similarkeyword +
                ", recommend=" + recommend +
                '}';
    }
}