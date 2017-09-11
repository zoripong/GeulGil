package com.five.high.emirim.geulgil.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {
    private final String SELECT_WORD = "selectedWord";

    LinearLayout mRoot;
    LinearLayout mMeanLocation;
    LinearLayout mSimilarLocation;

    DynamicButtonManager dynamicButtonManager;

    TextView mWord;
    TextView mPart;
    TextView mMean;
    SameSounds mItem;
//    Button mYesButton;
//    Button mNoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mItem = (SameSounds) getIntent().getSerializableExtra(SELECT_WORD);
        init();

        mWord.setText(mItem.getId());
        mPart.setText("[" + mItem.getWords().get(0).getmPart() + "]");

        if(mItem.isSingle()) {
            mMean.setText(mItem.getWords().get(0).getmMean());
            // TODO: 2017-09-11 모듈화
            String[] keywords = mItem.getWords().get(0).getmMeanKeyword();
            setKeyword(keywords, true);
            keywords = mItem.getWords().get(0).getmSimilarKeyword();
            setKeyword(keywords, false);
        }else{
            for(int i = 0; i<mItem.getWords().size(); i++){
                // // TODO: 2017-09-11 ..밍ㅁ..밍.. 
            }
        }
    }
    private void init(){
        mRoot = (LinearLayout)findViewById(R.id.root);
        mMeanLocation = (LinearLayout)findViewById(R.id.keywords_location_mean);
        mSimilarLocation = (LinearLayout)findViewById(R.id.keywords_location_similar);
        mWord = (TextView)findViewById(R.id.tv_word);
        mPart = (TextView)findViewById(R.id.tv_position);
        mMean = (TextView)findViewById(R.id.tv_mean);
        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), mRoot);
    }

    private void setKeyword(String [] keywords, boolean isMean){

        ArrayList<KeywordItem> keywordItems = new ArrayList<KeywordItem>();
        for(int i = 0; i<keywords.length; i++){
            keywordItems.add(new KeywordItem(keywords[i], isMean));
        }
        if(isMean)
            dynamicButtonManager.setDynamicButton(keywordItems, mMeanLocation, false);
        else
            dynamicButtonManager.setDynamicButton(keywordItems, mSimilarLocation, false);

    }
}
