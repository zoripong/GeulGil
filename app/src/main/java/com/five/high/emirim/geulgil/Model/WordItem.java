package com.five.high.emirim.geulgil.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by 유리 on 2017-06-16.
 */

public class WordItem implements Parcelable{
    private int id;
    private String mWord;
    private String mMean;
    private String mPart;
    private String [] mMeanKeyword;
    private String [] mSimilarKeyword;
    private int recommend;

    protected WordItem(Parcel in) {
        id = in.readInt();
        mWord = in.readString();
        mMean = in.readString();
        mPart = in.readString();
        mMeanKeyword = in.createStringArray();
        mSimilarKeyword = in.createStringArray();
        recommend = in.readInt();
    }

    public static final Creator<WordItem> CREATOR = new Creator<WordItem>() {
        @Override
        public WordItem createFromParcel(Parcel in) {
            return new WordItem(in);
        }

        @Override
        public WordItem[] newArray(int size) {
            return new WordItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mWord);
        dest.writeString(mMean);
        dest.writeString(mPart);
        dest.writeStringArray(mMeanKeyword);
        dest.writeStringArray(mSimilarKeyword);
        dest.writeInt(recommend);
    }
}