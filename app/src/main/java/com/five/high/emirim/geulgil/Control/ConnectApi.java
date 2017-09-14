package com.five.high.emirim.geulgil.Control;

import android.os.AsyncTask;
import android.util.Log;

import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import retrofit2.Call;

// // TODO: 2017-09-13 Progress Bar

public class ConnectApi{
    String title;
    ApiItem apiItem; //리턴할 ApiItem


    public ApiItem getRelativesResult(String request) {

        getSearchRecord(request);
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e("getRelativesResult","getRelativesResult 종료");
        return apiItem;
    }


    public SameSounds getDetailRecord(String request){
        //// TODO: 2017-09-14 김두리가 지금부터 해야하는 곳
        return null;
    }

    private void getSearchRecord(final String request) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                com.five.high.emirim.geulgil.Model.RetrofitService retrofitService
                        = com.five.high.emirim.geulgil.Model.RetrofitService.retrofit.create(com.five.high.emirim.geulgil.Model.RetrofitService.class);

                Call<ApiItem> call = retrofitService.getItem(request);


                try {
                    ApiItem returnApiItem = call.execute().body();
                    title=returnApiItem.getTitle();

                    HashSet<SameSounds> responeSamesound= new HashSet<SameSounds>(returnApiItem.getRelatives());
                    Iterator<SameSounds> iterator = responeSamesound.iterator();

                    HashSet<SameSounds> sameSoundsSet = new HashSet<SameSounds>();

                    while(iterator.hasNext()) {
                        SameSounds samesounds = iterator.next();
                        if (samesounds == null)
                            iterator.remove();

                        String ids = samesounds.getId();
                        //               Log.e("connect성공", String.valueOf(samesounds.getId()));

                        ArrayList<WordItem> wordItems = new ArrayList<WordItem>();

                        for(int i=0;i<samesounds.getWordItems().size();i++) { //samesound 파싱해서 wordItem 생성후 sameSoundsSet에 add
                            int id = samesounds.getWordItems().get(i).getId();
                            String word = samesounds.getWordItems().get(i).getWord();
                            String mean = samesounds.getWordItems().get(i).getMean();
                            String part = samesounds.getWordItems().get(i).getPart();
                            ArrayList<String> meankeyword = samesounds.getWordItems().get(i).getMeankeyword();
                            ArrayList<String> similarkeyword = samesounds.getWordItems().get(i).getSimilarkeyword();
                            int recommend = samesounds.getWordItems().get(i).getRecommend();

                            wordItems.add(new WordItem(id, word, mean, part, meankeyword, similarkeyword, recommend));

//                            Log.e("connect성공 id", String.valueOf(id));
//                            Log.e("connect성공 word", word);
//                            Log.e("connect성공 mean", mean);
//                            Log.e("connect성공 part", part);
//                            for(int j=0;j<meankeyword.length;j++)
//                                Log.e("connect성공 mkeyword", meankeyword[j]);
//                            for(int j=0;j<similarkeyword.length;j++)
//                                Log.e("connect성공 skeyword", similarkeyword[j]);
                        }

                        sameSoundsSet.add(new SameSounds(ids, wordItems));

                    }
                    //return
                    apiItem = new ApiItem(title, sameSoundsSet);
                    Log.e("connect 완료", String.valueOf(apiItem.getTitle()));

                    return null;


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }.execute();

    }

}