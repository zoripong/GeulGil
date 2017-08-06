package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.content.Intent;

import com.five.high.emirim.geulgil.Database.FireBaseData;
import com.five.high.emirim.geulgil.Model.WordItem;

/**
 * Created by 유리 on 2017-06-20.
 */

public class ControlData {
    private final String SEARCHING_STR_KEY = "searchingSTR";

    Context mContext;
    FireBaseData mFireBaseData;

    public ControlData(Context context) {
        this.mContext = context;
        this.mFireBaseData = new FireBaseData();
    }

    // Main -> SearchCardview : 검색하는 단어와 함께 액티비티 전환
    // SearchCardview -> ResultView : 선택한 카드뷰 단어와 함께 액티비티 전환
    public void myIntent(Context now, Class next, String str) {
        Intent intent = new Intent(now, next);
        intent.putExtra(SEARCHING_STR_KEY, str);
        now.startActivity(intent);
    }

//    public void myIntent(Context now, Class next, WordItem item){
//        Intent intent = new Intent(now, next);
//        intent.putExtra(SEARCHING_ITEM_KEY, str);
//        now.startActivity(intent);
//    }

    // 데이터베이스에서 가져와서 WordItem으로 반환
    public WordItem getWordItem(String searchingWord) {
        boolean search = true;
        String[] result = mFireBaseData.getWordItem(searchingWord);
        if (result != null) {
            String[] mean = result[5].split(",");
            String[] similar = result[6].split(",");
            return new WordItem(result[1], result[2], result[3], result[4], mean, similar, Integer.parseInt(result[7]));
        }else{
            if(search){
                search = false;
                return getWordItem(searchingWord);
            }else {
                return null;
            }
        }
    }
}
