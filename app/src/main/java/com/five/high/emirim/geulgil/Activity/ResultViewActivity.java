package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

public class ResultViewActivity extends AppCompatActivity {

        private final int DYNAMIC_VIEW_ID = 0x8000;
        private static int MAX_DYNAMIC_VIEW =0; // 다이나믹 버튼 개수

        private final String TAG = "Present WordName"; // 현재 word name
      private final String SEARCHING_STR_KEY = "searchingSTR";

    //----- 유사어
        String mSimilar[];
        private LinearLayout dynamicLayout;
        private int numButton = 0;

        TextView mTvWord;
        TextView mTvMean;
        TextView mTvCategory;

        LinearLayout Linear;

        Button dynamicButton;

        String mSearchingWord;

        ControlData controlData;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result_view);

            //--------유사어
            dynamicLayout = (LinearLayout) findViewById(R.id.dynamicArea);
            Linear = (LinearLayout)findViewById(R.id.layout);
            mTvWord = (TextView)findViewById(R.id.tv_word);
            mTvMean = (TextView)findViewById(R.id.tv_mean);
            mTvCategory = (TextView)findViewById(R.id.tv_category);

            controlData = new ControlData(this);

            Intent intent = getIntent();
            mSearchingWord = intent.getExtras().getString(SEARCHING_STR_KEY);

            setView();

            pushButton();

           //0x80001.setOnClickListener(ol);
            for(numButton=0;numButton <= MAX_DYNAMIC_VIEW;numButton++) {
                if (numButton >= MAX_DYNAMIC_VIEW) {
                    return;
                }

            }
            //Button button = (Button) findViewById(DYNAMIC_VIEW_ID + numButton);
            //(DYNAMIC_VIEW_ID + numButton).setOnClickListener( new View.OnClickListener()
        }

        private void pushButton() {
            for(numButton=0;numButton <= MAX_DYNAMIC_VIEW;numButton++) {
                if (numButton >= MAX_DYNAMIC_VIEW) {
                    return;
                }

                dynamicButton = new Button(this);
                dynamicButton.setId(DYNAMIC_VIEW_ID + numButton);

                dynamicButton.setText(mSimilar[numButton]);

                dynamicLayout.addView(dynamicButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                dynamicButton.setOnClickListener( new View.OnClickListener()
                {
                    public void onClick (View v){
                        mSearchingWord = ((Button)v).getText().toString();
                        Log.e(TAG, mSearchingWord );
                        popButton();
                        // TODO CHANGE!!!!
                        setView();
                        pushButton();
                    }
                });
            }
        }

        void popButton(){
            numButton=0;
            while (true) {
                if (numButton > MAX_DYNAMIC_VIEW) {
                    return;
                }
                Button dynamicButton = (Button) findViewById(DYNAMIC_VIEW_ID + numButton);
                dynamicLayout.removeView(dynamicButton);
                numButton++;
            }
        }

        private void setView(){
            WordItem result = controlData.getWordItem(mSearchingWord);
            mTvWord.setText(result.getmWord());
            mTvCategory.setText(result.getmCategory());
            mTvMean.setText(result.getmMean());
            mSimilar = result.getmSimilarKeyword();
            MAX_DYNAMIC_VIEW=mSimilar.length;
        }

}
