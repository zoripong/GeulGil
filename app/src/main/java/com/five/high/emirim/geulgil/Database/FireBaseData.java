package com.five.high.emirim.geulgil.Database;

import com.five.high.emirim.geulgil.Api.KeywordApi;
import com.five.high.emirim.geulgil.Api.MeanApi;
import com.five.high.emirim.geulgil.Model.KeywordApiItem;
import com.five.high.emirim.geulgil.Model.SelectMeanItem;

/**
 * Created by 유리 on 2017-08-06.
 */

public class FireBaseData {

    public FireBaseData(){
        // 파이어 베이스 연결
        // 인스턴스 생성과 동시에 파이어베이스 데이터베이스와 연결
    }

    public String[] getWordItem(String searchingWord){
        // 파이어베이스 직접 안짜봐서 셀렉트 하면 어떻게 반환되는지 몰라서 그냥 String[] 배열로 해놨어
        // 그냥 한 레코드 다 가져올 수 있게 해주세욥 두리님
        // 상황에 따라서 바뀌어도 되고, 지켜줬으면 좋겠는건 반환 타입이랑, 들어오는 매개변수 타입,
        // 그리고 없으면 setWord실행하고 있으면 레코드 한 줄 스트링 배열로 반환하는것만 지켜주면 문제 없을 듯!
        // 데이터 베이스에서 셀렉트


        //// TODO: 2017-08-06 sSorM에 따라 쿼리문이 달라짐
//        String [] result = 파이어 베이스 select 함수 ex> select * from table where keyword like '%'+searchingword+'%';

//        if(result == null){
////            찾지못할경우
//          insertWordItem(searchingWord);
//          return null;
//        }else{
////             있을경우

            //// TODO: 2017-08-06 : result[]
            /// result[0] - id / result[1] - word / result[2] - mean / result[3] - part
            /// result[4] - category / result[5] - mean_keyword / result[6] - similar_keyword / result[7] - recommand

//          return result;
//        }

        String result[] = {"1", "나무", "줄기나 가지가 목질로 된 여러해살이 식물", "null", "명사", "줄기,가지,목질,여러해살이,식물","땔나무,땔감,목재,수목,재목", "1"};
        return result;
//        return null;


    }

// 검샘ㄱ창에 검색할 때마다 실행.. Thread .......?
    public void collectData(String searching){
//        String [] result = 파이어 베이스 select 함수 ex> select * from table where word = searaching;

        // TODO: 2017-08-06 앱 진행 과정에 영향 없이 뒤에서 실행되도록.. -> 앱은 다음 화면으로 넘어가있어야 함.
        // 또는 로딩 화면  빙그르르 프로세스 바

//        if(fail){
//            insertWordItem(searching);
//        }

    }
    private void insertWordItem(String insertWord){
        // 데이터 베이스에 insert
        //TODO: 생성자 규칙에 맞춰서 인스턴스 생성!!!!!! 일단 기본 생성자로 해놀게욥
        KeywordApi keywordApi = new KeywordApi();
        MeanApi meanApi = new MeanApi();

        SelectMeanItem meanItem = meanApi.getXmlData(insertWord); // word, mean, category, part
        KeywordApiItem keywordItem = keywordApi.getKeywordItem(insertWord); // mean keyword [] , similar keyword []

        // // TODO: 2017-08-06 : meanItem과 keywordItem을 이용해서 insert
        // Insert할 때, keyword 들은 "," 콤마로 연결해서 하나의 스트링으로 만들어서 넣어줘!
        // 나중에 꺼내쓸때 split(",")으로 다시 배열로 바꿀거야.

    }

}
