package com.five.high.emirim.geulgil.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class DetailViewActivity extends AppCompatActivity {
    private int SEARCHING_WORD_ID = 0x8000;
    private final String SELECT_WORD = "selectedWord";

    LinearLayout mRoot;
    LinearLayout mMeanKeywordsLocation;
    LinearLayout mSimilarKeywordsLocation;
    LinearLayout mMeanLocation;

    DynamicButtonManager dynamicButtonManager;
    SharedPreferencesManager sharedPreferencesManager;

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
        ArrayList<SearchRecordItem> items = sharedPreferencesManager.loadSharedPreferencesLogList(getApplicationContext());
        items.add(item);

        HashSet<SearchRecordItem> set = new HashSet<>(items);
        items = new ArrayList<SearchRecordItem>(set);

        sharedPreferencesManager.saveSharedPreferencesLogList(getApplicationContext(), items);
        for(int i = 0; i<items.size(); i++){
            Log.e("before", items.get(i).toString());
        }
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
        sharedPreferencesManager = new SharedPreferencesManager();
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

}
