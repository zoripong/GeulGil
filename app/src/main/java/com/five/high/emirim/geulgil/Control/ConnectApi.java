package com.five.high.emirim.geulgil.Control;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Activity.DetailViewActivity;
import com.five.high.emirim.geulgil.Activity.ResultCardViewActivity;
import com.five.high.emirim.geulgil.Adapter.DialogManager;
import com.five.high.emirim.geulgil.Adapter.ProgressDialogManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConnectApi{
    private final int SEARCHACTIVITY = 1;
    private final int DETAILVIEWACTIVITY=2;
    private final String SELECT_WORD = "selectedWord";

    ApiItem apiItem; //리턴할 ApiItem
    SameSounds sameSounds;
    Context mContext;
    Activity nowActivity;

    DialogManager dialogManager;

    com.five.high.emirim.geulgil.Model.RetrofitService retrofitService;
    ProgressDialog progressDialog;
    ProgressDialogManager progressDialogManager;


    public ConnectApi(Context mContext, Activity nowActivity) {
        this.mContext = mContext;
        this.nowActivity = nowActivity;
        dialogManager = new DialogManager(mContext);
        progressDialog = new ProgressDialog(mContext);
        progressDialogManager = new ProgressDialogManager();
        retrofitService = com.five.high.emirim.geulgil.Model.RetrofitService.retrofit.create(com.five.high.emirim.geulgil.Model.RetrofitService.class);
    }

    public SameSounds getSameSounds() {
        Log.e("getSameSounds", String.valueOf(sameSounds));
        return sameSounds;
    }

    public void getRelativesResult(final KeywordItem keywordItem) {
        String request = keywordItem.getWord() + "/" + String.valueOf(keywordItem.isMean()) + "/";

        isNetWork();
        progressDialogManager.showDialog(progressDialog);

        Call<ApiItem> call = retrofitService.getApiItem(request);
        call.enqueue(new Callback< ApiItem >() {

            @Override
            public void onResponse(Call<ApiItem> call, Response<ApiItem> response) {
                apiItem = response.body();
                progressDialogManager.dismissDialog(progressDialog);
                Log.e("connect 완료", String.valueOf(apiItem.getTitle()));
                Log.e("this", "Yes!");

                if(apiItem == null) {
                    dialogManager.showDialog("결과가 없습니다.", 1);
                }else {
                    apiItem = seperateSet(apiItem); // 동음이의어와 단일어 구분
                    M.mResult.add(apiItem.getRelatives());
                    M.mKeywordItem.add(keywordItem);
                    Intent intent = new Intent(nowActivity, ResultCardViewActivity.class);
                    nowActivity.startActivity(intent);
                    nowActivity.finish();
                }
            }

            @Override
            public void onFailure(Call<ApiItem> call, Throwable t) {
                progressDialog.dismiss();
                dialogManager.showDialog("서버와의 연결에 실패하였습니다.", 1);
            }
        });
    }

    public void getDetailRecord(String request, final int type){
        isNetWork();
        progressDialogManager.showDialog(progressDialog);

        Call<SameSounds> call = retrofitService.getSameSounds(request);
        call.enqueue(new Callback< SameSounds >() {

            @Override
            public void onResponse(Call<SameSounds> call, Response<SameSounds> response) {
                sameSounds = response.body();
                progressDialog.dismiss();
                Log.e("connect 완료", String.valueOf(sameSounds.getId()));
                Log.v("this", "Yes!");

                switch (type) {
                    case SEARCHACTIVITY:
                        if (sameSounds == null) {
                            dialogManager.showDialog("단어를 찾을 수 없습니다.", 1);
                        } else {
                            Intent intent = new Intent(nowActivity, DetailViewActivity.class);
                            intent.putExtra(SELECT_WORD, sameSounds);
                            nowActivity.startActivity(intent);
                        }
                        break;
                    case DETAILVIEWACTIVITY:
                        DynamicButtonManager dynamicButtonManager = new DynamicButtonManager(mContext, (LinearLayout)nowActivity.findViewById(R.id.root), nowActivity);

                        TextView tvWord = (TextView)nowActivity.findViewById(R.id.tv_word);
                        TextView tvPosition = (TextView)nowActivity.findViewById(R.id.tv_position);
                        LinearLayout meanKeywordsLocation = (LinearLayout)nowActivity.findViewById(R.id.keywords_location_mean);
                        LinearLayout similarKeywordsLocation = (LinearLayout) nowActivity.findViewById(R.id.keywords_location_similar);
                        LinearLayout meanLocation = (LinearLayout) nowActivity.findViewById(R.id.mean_location);

                        //// TODO: 2017-09-16  samesounds가 0일때처리
                        tvWord.setText(sameSounds.getId());
                        tvPosition.setText("[" + sameSounds.getWordItems().get(0).getPart() + "]");
                        dynamicButtonManager.convertStringToModel(sameSounds.getWordItems().get(0).getMeankeyword(), meanKeywordsLocation, true);
                        dynamicButtonManager.convertStringToModel(sameSounds.getWordItems().get(0).getSimilarkeyword(), similarKeywordsLocation, false);
                        dynamicButtonManager.setMeanText(sameSounds, meanLocation, meanKeywordsLocation, similarKeywordsLocation);

                        // 검색 기록 저장
                        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
                        SearchRecordItem searchRecordItem = new SearchRecordItem(sameSounds.getId(), sameSounds.getWordItems().get(0).getMean());
                        ArrayList<SearchRecordItem> items = sharedPreferencesManager.loadSharedPreferencesLogList(mContext);
                        items.add(searchRecordItem);
                        HashSet<SearchRecordItem> set = new HashSet<>(items);
                        items = new ArrayList<SearchRecordItem>(set);
                        sharedPreferencesManager.saveSharedPreferencesLogList(mContext, items);
                        // 검색 기록 저장
                        break;
                }

            }

            @Override
            public void onFailure(Call<SameSounds> call, Throwable t) {
                progressDialogManager.dismissDialog(progressDialog);
                dialogManager.showDialog("서버와의 연결에 실패하였습니다.", 1);
            }
        });


    }

    private void isNetWork(){
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService (Context.CONNECTIVITY_SERVICE);
        boolean isMobileAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        boolean isMobileConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifiAvailable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean isWifiConnect = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if (!((isWifiAvailable && isWifiConnect) || (isMobileAvailable && isMobileConnect))){
            dialogManager.showDialog("네트워크 연결에 문제가 발생하였습니다.", 1);
        }
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