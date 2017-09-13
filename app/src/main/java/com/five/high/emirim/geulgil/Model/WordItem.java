package com.five.high.emirim.geulgil.Model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 유리 on 2017-06-16.
 */

public class WordItem implements Serializable{
    private int id;
    private String word;
    private String mean;
    private String part;
    private String [] meankeyword;
    private String [] similarkeyword;
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

    public String[] getMeankeyword() {
        return meankeyword;
    }

    public String[] getSimilarkeyword() {
        return similarkeyword;
    }

    public int getRecommend() {
        return recommend;
    }

    public WordItem(int id, String word, String mean, String part, String [] meankeyword, String [] similarkeyword, int recommend) {
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
                ", meankeyword=" + Arrays.toString(meankeyword) +
                ", similarkeyword=" + Arrays.toString(similarkeyword) +
                ", recommend=" + recommend +
                '}';
    }

}