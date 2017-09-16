package com.five.high.emirim.geulgil.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
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
        Log.e("Set the DetailView", mItem.getWordItems().get(0).toString());

        mPart.setText("[" + mItem.getWordItems().get(0).getPart() + "]");
        Log.e("MeanKeyword", mItem.getWordItems().get(0).getMeankeyword().size()+"개");


        dynamicButtonManager.convertStringToModel(mItem.getWordItems().get(0).getMeankeyword(), mMeanKeywordsLocation, true);
        dynamicButtonManager.convertStringToModel(mItem.getWordItems().get(0).getSimilarkeyword(), mSimilarKeywordsLocation, false);

        dynamicButtonManager.setMeanText(mItem, mMeanLocation, mMeanKeywordsLocation, mSimilarKeywordsLocation);
        // 검색 기록 저장
        SearchRecordItem item = new SearchRecordItem(mItem.getId(), mItem.getWordItems().get(0).getMean());
        ArrayList<SearchRecordItem> items = sharedPreferencesManager.loadSharedPreferencesLogList(getApplicationContext());
        items.add(item);

        HashSet<SearchRecordItem> set = new HashSet<>(items);
        items = new ArrayList<SearchRecordItem>(set);

        sharedPreferencesManager.saveSharedPreferencesLogList(getApplicationContext(), items);
        // 검색 기록 저장
    }

    private void init(){
        mRoot = (LinearLayout)findViewById(R.id.root);
        mMeanKeywordsLocation = (LinearLayout)findViewById(R.id.keywords_location_mean);
        mSimilarKeywordsLocation = (LinearLayout)findViewById(R.id.keywords_location_similar);
        mWord = (TextView)findViewById(R.id.tv_word);
        mMeanLocation = (LinearLayout)findViewById(R.id.mean_location);
        mPart = (TextView)findViewById(R.id.tv_position);
        dynamicButtonManager = new DynamicButtonManager(DetailViewActivity.this, mRoot, this);
        sharedPreferencesManager = new SharedPreferencesManager();
    }



}
