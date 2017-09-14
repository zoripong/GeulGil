package com.five.high.emirim.geulgil.Model;

/**
 * Created by 유리 on 2017-09-09.
 */

public class SearchRecordItem {
    private String word;
    private String mean;

    public SearchRecordItem(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }


    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    @Override
    public String toString() {
        return "SearchRecordItem{" +
                "word='" + word + '\'' +
                ", mean='" + mean + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchRecordItem that = (SearchRecordItem) o;

        if (!word.equals(that.word)) return false;
        return mean.equals(that.mean);

    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + mean.hashCode();
        return result;
    }
}
