package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.util.Log;

import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-20.
 */

public class ControlData {
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

    // Word Item -> api에서 SameSounds -> Word Item 으로 return
    // string에 해당하는
    public HashSet<WordItem> searchingWord(HashSet<WordItem> hashSet, KeywordItem word) {
        //TODO: 동음이의어 처리,,,  ㅜㅜㅜ

        String request = word.getWord() + "!" + String.valueOf(word.isMean());
        ApiItem apiItem = api.getRelativesResult(request);

//        Iterator<SameSounds> getIterator = apiItem.getRelatives().iterator();

        seperateSet(apiItem);

        HashSet<SameSounds> newSet = new HashSet<SameSounds>();

        while(getIterator.hasNext()){
            SameSounds now = getIterator.next();
            if(hashSet.contains(now) || count == 0){
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

    private void seperateSet(ApiItem apiItem) {

    }
}
