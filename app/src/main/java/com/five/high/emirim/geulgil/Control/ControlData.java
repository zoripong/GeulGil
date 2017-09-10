package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.util.Log;

import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.Model.WordItem;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-20.
 */

public class ControlData {
    private final String SEARCHING_STR_KEY = "searchingSTR";
    private int count;

    Context mContext;
    ConnectApi api;


    public ControlData (){
        api = new ConnectApi();
        count = 0;
    }

    public ControlData(Context context) {
        this();
        this.mContext = context;
    }


    // string에 해당하는
    public HashSet<WordItem> searchingWord(HashSet<WordItem> hashSet, SearchingWord word) {
        //TODO:

        String request = word.getWord() + "!" + String.valueOf(word.isMean());
        ApiItem relatives = api.getRelativesResult(request);
        Iterator<WordItem> getIterator = relatives.getmWordItems().iterator();

        HashSet<WordItem> newSet = new HashSet<WordItem>();

        // 동일한 WordItem만 NewSet에 넣어줌
        // 검색 결과가 없을 경우 처음 받은 hashSet을 넘김
        // Set 특성상 동일한 데이터가 존재하지 않음
        while (getIterator.hasNext()) {
            WordItem now = getIterator.next();
            // 동일한 WordItem이 있거나, 첫 검색이거나
            if (hashSet.contains(now) || count == 0) {
                Log.e("같니?", now.toString());
                newSet.add(now);
            }
        }

        if (newSet.size() == 0) {
            M.isNull = true;
            return hashSet;
        } else {
            M.isNull = false;
            count++;
            return newSet;
        }
    }

}
