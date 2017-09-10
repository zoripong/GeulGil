package com.five.high.emirim.geulgil.Model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 유리 on 2017-06-16.
 */

public class WordItem implements Serializable{
    private int id;
    private String mWord;
    private String mMean;
    private String mPart;
    private String [] mMeanKeyword;
    private String [] mSimilarKeyword;
    private int recommend;

    public int getId() {
        return id;
    }

    public String getmWord() {
        return mWord;
    }

    public String getmMean() {
        return mMean;
    }

    public String getmPart() {
        return mPart;
    }

    public String[] getmMeanKeyword() {
        return mMeanKeyword;
    }

    public String[] getmSimilarKeyword() {
        return mSimilarKeyword;
    }

    public int getRecommend() {
        return recommend;
    }

    public WordItem(int id, String mWord, String mMean, String mPart, String [] mMeanKeyword, String [] mSimilarKeyword, int recommend) {
        this.id = id;
        this.mWord = mWord;
        this.mMean = mMean;
        this.mPart = mPart;
        this.mMeanKeyword = mMeanKeyword;
        this.mSimilarKeyword = mSimilarKeyword;
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "WordItem{" +
                "id=" + id +
                ", mWord='" + mWord + '\'' +
                ", mMean='" + mMean + '\'' +
                ", mPart='" + mPart + '\'' +
                ", mMeanKeyword=" + Arrays.toString(mMeanKeyword) +
                ", mSimilarKeyword=" + Arrays.toString(mSimilarKeyword) +
                ", recommend=" + recommend +
                '}';
    }

}