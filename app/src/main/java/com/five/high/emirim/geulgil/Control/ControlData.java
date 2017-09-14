package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.util.Log;

import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-20.
 */

public class ControlData {
    Context mContext;
    ConnectApi connectApi;

    public ControlData (){
        connectApi = new ConnectApi();
    }

    public ControlData(Context context) {
        this();
        this.mContext = context;
    }

    // Word Item -> api에서 SameSounds -> Word Item 으로 return
    // string에 해당하는
    public HashSet<SameSounds> searchingWord(KeywordItem word) {
        // hashSet = 검색 결과 누적 집합
        // word = 요청 검색어
        // apiItem = 새로운 검색 결과

        String request = word.getWord() + "/" + String.valueOf(word.isMean())+"/";

        ApiItem apiItem = null;

        apiItem = connectApi.getRelativesResult(request);

        if(apiItem == null) {
            Log.e("connect 실패", "apiItem == null");
            M.isNull = true;
            return null;
        }else {
            M.isNull = false;
            apiItem = seperateSet(apiItem); // 동음이의어와 단일어 구분
            M.mResult.add(apiItem.getRelatives());
        }

        return apiItem.getRelatives();
    }

    private ApiItem seperateSet(ApiItem apiItem) {
        HashSet<SameSounds> relatives = apiItem.getRelatives();
        Iterator<SameSounds> iterator = relatives.iterator();

        while(iterator.hasNext()){
            SameSounds now = iterator.next();
            if(now.getWordItems().size() == 1)
                now.setSingle(true);
            else if(now.getWordItems().size() > 1)
                now.setSingle(false);
            else
                iterator.remove();
        }
        return apiItem;
    }

}

