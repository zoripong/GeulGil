package com.five.high.emirim.geulgil.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-08.
 */

public class DynamicButtonManager {
    private int SEARCHING_WORD_ID = 0x8000;
    Context mContext;
    LinearLayout mRootLayout;
    String strColor = null;

    public DynamicButtonManager(Context context, LinearLayout root) {
        mContext = context;
        mRootLayout = root;
    }

    private void setDynamicButton(final SearchingWord word, final LinearLayout location, boolean clickable){
        Log.e("DYNAMIC BUTTON", "SET OK");

        final TextView searchingWord = new TextView(mContext);
        searchingWord.setText(word.getWord());

        searchingWord.setId(SEARCHING_WORD_ID++);

        customizingBackground(searchingWord, word.isMean());

        if(clickable){
            final boolean[] isLongClicked = {false};
            //롱클릭 리스너
            searchingWord.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    isLongClicked[0] = true;
                    Toast.makeText(mContext, "longClick", Toast.LENGTH_SHORT).show();
                    searchingWord.setBackgroundResource(R.drawable.black_bt);
                    //  searchingWord.setText("");
                    searchingWord.setTextColor(Color.parseColor("#24FFFFFF"));
                    return true;
                }
            });
            searchingWord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(isLongClicked[0]){
                        Toast.makeText(mContext, "remove", Toast.LENGTH_SHORT).show();
                        isLongClicked[0] = false;
                        location.removeView(searchingWord);
                    }
                }
            });
            mRootLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (isLongClicked[0]){
                        isLongClicked[0] = false;
                        Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();

                        searchingWord.setText(word.getWord());
                        if(word.isMean()){
                            searchingWord.setBackgroundResource(R.drawable.keyword_button_mean);
                            strColor = "#FFFFFF";
                        }
                        else {
                            searchingWord.setBackgroundResource(R.drawable.keyword_button_similar);
                            strColor = "#1583ff";
                        }
                        searchingWord.setTextColor(Color.parseColor(strColor));

                    }
                }
            });
        }

        location.addView(searchingWord);

    }

    public void setDynamicButton(ArrayList<SearchingWord> words, LinearLayout location, boolean clickable){
        for(int i = 0; i < words.size(); i++) {
            setDynamicButton(words.get(i), location, clickable);
        }
    }

    private void customizingBackground(TextView word, boolean isMean){
        String strColor;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT    , convertDpToPixel(25));
        params.setMargins(convertDpToPixel(5), 0, convertDpToPixel(5), 0); // left top right bottom
        word.setLayoutParams(params);
        word.setGravity(Gravity.CENTER);
        if(isMean) {
            word.setBackgroundResource(R.drawable.keyword_button_mean);
            strColor = "#FFFFFF";
        }else{
            word.setBackgroundResource(R.drawable.keyword_button_similar);
            strColor = "#1583ff";
        }
        word.setTextColor(Color.parseColor(strColor));
    }

    private int convertDpToPixel(float dp){
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)px;
    }


}
