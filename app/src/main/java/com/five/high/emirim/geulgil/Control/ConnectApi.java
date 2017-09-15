package com.five.high.emirim.geulgil.Control;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    String ids;
    ApiItem apiItem; //리턴할 ApiItem
    SameSounds sameSounds;
    String request;
    boolean returnType;
    Context mContext;

    private ConnectServer connectServer;

    public ConnectApi(Context mContext) {
        this.mContext = mContext;
    }

    public ApiItem getRelativesResult(String request) {
        this.request=request;
        this.returnType=true;
        connectServer = new ConnectServer();
        connectServer.execute();

        synchronized (connectServer) {
            try {
                connectServer.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return apiItem;
    }


    public SameSounds getDetailRecord(String request){
        //// TODO: 2017-09-14 김두리가 지금부터 해야하는 곳
        this.request=request;
        this.returnType=false;

        connectServer = new ConnectServer();
        connectServer.execute();

        synchronized (connectServer) {
            try {
                connectServer.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        return sameSounds;
    }


    public class ConnectServer extends AsyncTask<Void, Void, String>{

        ProgressDialog asyncDialog ;
//        = new ProgressDialog(v);

        @Override
        protected void onPreExecute() {
            isNetWork();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            com.five.high.emirim.geulgil.Model.RetrofitService retrofitService
                    = com.five.high.emirim.geulgil.Model.RetrofitService.retrofit.create(com.five.high.emirim.geulgil.Model.RetrofitService.class);


            synchronized (this) {
                if (returnType) {
                    Call<ApiItem> call = retrofitService.getApiItem(request);
                    try {
                        ApiItem returnItem = null;
                        returnItem = call.execute().body();

                        title = returnItem.getTitle();

                        HashSet<SameSounds> responseSamesound = new HashSet<SameSounds>(returnItem.getRelatives());
                        Iterator<SameSounds> iterator = responseSamesound.iterator();

                        HashSet<SameSounds> sameSoundsSet = new HashSet<SameSounds>();

                        while (iterator.hasNext()) {
                            SameSounds samesounds = iterator.next();
                            if (samesounds == null)
                                iterator.remove();

                            String ids = samesounds.getId();

                            ArrayList<WordItem> wordItems = new ArrayList<WordItem>();

                            for (int i = 0; i < samesounds.getWordItems().size(); i++) { //samesound 파싱해서 wordItem 생성후 sameSoundsSet에 add
                                int id = samesounds.getWordItems().get(i).getId();
                                String word = samesounds.getWordItems().get(i).getWord();
                                String mean = samesounds.getWordItems().get(i).getMean();
                                String part = samesounds.getWordItems().get(i).getPart();
                                ArrayList<String> meankeyword = samesounds.getWordItems().get(i).getMeankeyword();
                                ArrayList<String> similarkeyword = samesounds.getWordItems().get(i).getSimilarkeyword();
                                int recommend = samesounds.getWordItems().get(i).getRecommend();

                                wordItems.add(new WordItem(id, word, mean, part, meankeyword, similarkeyword, recommend));

                                Log.e("connect성공 id", String.valueOf(id));

                            }

                            sameSoundsSet.add(new SameSounds(ids, wordItems));

                        }
                        //return
                        apiItem = new ApiItem(title, sameSoundsSet);
                        Log.e("connect 완료", String.valueOf(apiItem.getTitle()));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectServer.notify();
                    return String.valueOf(apiItem.getTitle());

                } else {//getDetailRecord
                    Call<SameSounds> call = retrofitService.getSameSounds(request);

                    try {
                        SameSounds returnItem = null;
                        returnItem = call.execute().body();

                        ids = returnItem.getId();


                        ArrayList<WordItem> wordItems = new ArrayList<WordItem>();

                        for (int i = 0; i < returnItem.getWordItems().size(); i++) {
                            int id = returnItem.getWordItems().get(i).getId();
                            String word = returnItem.getWordItems().get(i).getWord();
                            String mean = returnItem.getWordItems().get(i).getMean();
                            String part = returnItem.getWordItems().get(i).getPart();
                            ArrayList<String> meankeyword = returnItem.getWordItems().get(i).getMeankeyword();
                            ArrayList<String> similarkeyword = returnItem.getWordItems().get(i).getSimilarkeyword();
                            int recommend = returnItem.getWordItems().get(i).getRecommend();

                            wordItems.add(new WordItem(id, word, mean, part, meankeyword, similarkeyword, recommend));

                            Log.e("connect성공 id", String.valueOf(id));
                        }

                        //return
                        sameSounds = new SameSounds(ids, wordItems);
                        Log.e("connect 완료", String.valueOf(sameSounds.getId()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectServer.notify();
                     return String.valueOf(apiItem.getTitle());


                }

            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result != null){
                Log.e("ConnectApi", "result = " + result);
            }else{
                Log.e("ConnectApi", "단어 관련 정보가 없습니다.");
            }
            //asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

    void isNetWork(){
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService (Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();


        if (!((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect))){
            //TODO : 네트워크 연결 안되어있을 때 Dialog 연결해주세요 !!!!!!!!
            Log.e("네트워크 상태 (연결 오류) : ","연결 안 되어 있습니다");
            Toast.makeText(mContext, "네트워크 연결 안되어있습니다.",Toast.LENGTH_SHORT).show();


        }else{
            //TODO : 네트워크 연결 되어있을 때
            Log.e("네트워크 상태 (연결 성공)","연결 되어 있습니다");
            Toast.makeText(mContext, "네트워크 연결 되어있습니다.",Toast.LENGTH_SHORT).show();


        }
    }




}