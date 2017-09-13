package com.five.high.emirim.geulgil.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailViewActivity extends AppCompatActivity {
    private int SEARCHING_WORD_ID = 0x8000;
    private final String RECORD_SEARCHING = "recordOfSearching";
    private final String SELECT_WORD = "selectedWord";

    LinearLayout mRoot;
    LinearLayout mMeanKeywordsLocation;
    LinearLayout mSimilarKeywordsLocation;
    LinearLayout mMeanLocation;

    DynamicButtonManager dynamicButtonManager;

    TextView mWord;
    TextView mPart;
    SameSounds mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mItem = (SameSounds) getIntent().getSerializableExtra(SELECT_WORD);
        init();

        mWord.setText(mItem.getId());
        mPart.setText("[" + mItem.getWordItems().get(0).getPart() + "]");
        setKeyword(mItem.getWordItems().get(0).getMeankeyword(), true);
        setKeyword(mItem.getWordItems().get(0).getSimilarkeyword(), false);

        for(int i = 0; i<mItem.getWordItems().size(); i++){
            TextView meanText = new TextView(getApplicationContext());
            meanText.setText(mItem.getWordItems().get(i).getMean());
            meanText.setId(SEARCHING_WORD_ID++);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0); // left top right bottom
            meanText.setLayoutParams(params);

            final int finalI = i;
            meanText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    setKeyword(mItem.getWordItems().get(finalI).getMeankeyword(), true);
                    setKeyword(mItem.getWordItems().get(finalI).getMeankeyword(), true);
                }
            });
        }

        // 검색 기록 저장
        SearchRecordItem item = new SearchRecordItem(mItem.getId(), mItem.getWordItems().get(0).getMean());
        ArrayList<SearchRecordItem> items = loadSharedPreferencesLogList(getApplicationContext());
        items.add(item);
        saveSharedPreferencesLogList(getApplicationContext(), items);
        // 검색 기록 저장
    }

    private void init(){
        mRoot = (LinearLayout)findViewById(R.id.root);
        mMeanKeywordsLocation = (LinearLayout)findViewById(R.id.keywords_location_mean);
        mSimilarKeywordsLocation = (LinearLayout)findViewById(R.id.keywords_location_similar);
        mWord = (TextView)findViewById(R.id.tv_word);
        mMeanLocation = (LinearLayout)findViewById(R.id.mean_location);
        mPart = (TextView)findViewById(R.id.tv_position);
        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), mRoot);
    }

    private void setKeyword(String [] keywords, boolean isMean){

        ArrayList<KeywordItem> keywordItems = new ArrayList<KeywordItem>();
        for(int i = 0; i<keywords.length; i++){
            keywordItems.add(new KeywordItem(keywords[i], isMean));
        }
        if(isMean)
            dynamicButtonManager.setDynamicButton(keywordItems, mMeanKeywordsLocation, false);
        else
            dynamicButtonManager.setDynamicButton(keywordItems, mSimilarKeywordsLocation, false);

    }
    private void saveSharedPreferencesLogList(Context context, ArrayList<SearchRecordItem> searchRecordItems) {
        SharedPreferences mPrefs = context.getSharedPreferences(RECORD_SEARCHING, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(searchRecordItems);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }


    private ArrayList<SearchRecordItem> loadSharedPreferencesLogList(Context context) {
        ArrayList<SearchRecordItem> recordItems;
        SharedPreferences mPrefs = context.getSharedPreferences(RECORD_SEARCHING, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            recordItems = new ArrayList<SearchRecordItem>();
        } else {
            Type type = new TypeToken<List<SearchRecordItem>>() {
            }.getType();
            recordItems = gson.fromJson(json, type);
        }
        return recordItems;
    }
}
