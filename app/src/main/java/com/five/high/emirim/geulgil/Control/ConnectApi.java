package com.five.high.emirim.geulgil.Control;

import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;

import java.util.HashSet;

/**
 * Created by 유리 on 2017-08-17.
 */

public class ConnectApi {

    // TODO : 서버와 연결하여 json을 가지고 와서 파싱한 APIItem  리턴
    public ApiItem getRelativesResult(String word) {
        /*
        Test용 코드
         */
        HashSet<WordItem> set = new HashSet<WordItem>();
        String [] keywords = {"가", "나", "다", "라", "마"};

 
        set.add(new WordItem(1, "사자", "어흥하는 육식동물", "명사", keywords, keywords, 0));
        set.add(new WordItem(2, "토끼", "깡총하는 초식동물", "명사", keywords, keywords, 0));
        set.add(new WordItem(3, "타자", "날렵한 육식동물", "명사", keywords, keywords, 0));
        set.add(new WordItem(4, "타조", "엄청큰 닭", "명사", keywords, keywords, 0));
        set.add(new WordItem(5, "피카츄", "백만볼트", "명사", keywords, keywords, 0));
        set.add(new WordItem(6, "강아지", "멍멍하는 잡식동물", "명사", keywords, keywords, 0));


        int ids[]= {1, 2, 3, 4, 5, 6};
        HashSet<SameSounds> sameSoundsesSet = new HashSet<SameSounds>();
        sameSoundsesSet.add(new SameSounds("몰랑이", ids));


        ApiItem result = new ApiItem("기린", set, sameSoundsesSet);



        return result;
    }
}
