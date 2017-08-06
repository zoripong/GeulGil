package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-06-16.
 */

public class WordItem {
    private String mWord;
    private String mMean;
    private String mPart;
    private String mCategory;
    private String [] mMeanKeyword;
    private String [] mSimilarKeyword;
    private int recommend;

    public String getmWord() {
        return mWord;
    }

    public String getmMean() {
        return mMean;
    }

    public String getmPart() {
        return mPart;
    }

    public String getmCategory() {
        return mCategory;
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

    public WordItem(String mWord, String mMean, String mPart, String mCategory, String [] mMeanKeyword, String [] mSimilarKeyword, int recommend) {
        this.mWord = mWord;
        this.mMean = mMean;
        this.mPart = mPart;
        this.mCategory = mCategory;
        this.mMeanKeyword = mMeanKeyword;
        this.mSimilarKeyword = mSimilarKeyword;
        this.recommend = recommend;
    }
}