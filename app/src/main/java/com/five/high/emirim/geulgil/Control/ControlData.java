package com.five.high.emirim.geulgil.Control;

import android.content.Context;

import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.WordItem;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-20.
 */

public class ControlData {
    private final String SEARCHING_STR_KEY = "searchingSTR";

    Context mContext;
    ConnectApi api;

    public ControlData (){
        api = new ConnectApi();
    }

    public ControlData(Context context) {
        this();
        this.mContext = context;
    }

    public HashSet<WordItem> searchingWord(HashSet<WordItem> hashSet, String word){
        //TODO:
        ApiItem relatives = api.getRelativesResult(word);
        Iterator<WordItem> iterator = relatives.getmWordItems().iterator();

        while(iterator.hasNext())
            hashSet.add(iterator.next());



        return hashSet;
    }

}
