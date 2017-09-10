package com.five.high.emirim.geulgil.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.WordItem;
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
    WordItem mItem;
//    Button mYesButton;
//    Button mNoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mItem = (WordItem) getIntent().getSerializableExtra(SELECT_WORD);
        init();

        mWord.setText(mItem.getmWord());
        mPart.setText("["+mItem.getmPart()+"]");
        mMean.setText(mItem.getmMean());

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), mRoot);

        String [] keywords = mItem.getmMeanKeyword();
        setKeyword(keywords, true);
        keywords = mItem.getmSimilarKeyword();
        setKeyword(keywords, false);

    }
    private void init(){
        mRoot = (LinearLayout)findViewById(R.id.root);
        mMeanLocation = (LinearLayout)findViewById(R.id.keywords_location_mean);
        mSimilarLocation = (LinearLayout)findViewById(R.id.keywords_location_similar);
        mWord = (TextView)findViewById(R.id.tv_word);
        mPart = (TextView)findViewById(R.id.tv_position);
        mMean = (TextView)findViewById(R.id.tv_mean);


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
//    public void onBackPressed() {
//        Dialog dialog = new Dialog(DetailViewActivity.this, R.style.MyDialog);
//        dialog.setContentView(R.layout.dialog);
//        dialog.show();
//
//        mYesButton =(Button)dialog.findViewById(R.id.dialog_button_yes);
//        mNoButton =(Button)dialog.findViewById(R.id.dialog_button_no);
//        mYesButton.setEnabled(true);
//        mNoButton.setEnabled(true);
//
//        mYesButton.setOnClickListener(new View.OnClickListener(){
//            public  void onClick(View v){
//
//                Intent intent =new Intent(DetailViewActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        mNoButton.setOnClickListener(new View.OnClickListener(){
//            public  void onClick(View v){
//                finish();
//            }
//        });
//    }
}
