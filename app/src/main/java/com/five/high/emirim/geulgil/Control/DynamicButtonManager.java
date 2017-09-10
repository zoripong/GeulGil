package com.five.high.emirim.geulgil.Control;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-08.
 */

public class DynamicButtonManager {
    private int SEARCHING_WORD_ID = 0x8000;
    Context mContext;
    LinearLayout mRootLayout;

    public DynamicButtonManager(Context context, LinearLayout root) {
        mContext = context;
        mRootLayout = root;
    }

    private TextView setDynamicButton(final KeywordItem word, final LinearLayout location, boolean clickable){
        Log.e("DYNAMIC BUTTON", "SET OK");

        final TextView searchingWord = new TextView(mContext);
        searchingWord.setText(word.getWord());

        searchingWord.setId(SEARCHING_WORD_ID++);

        customizingBackground(searchingWord, word.isMean());

        location.addView(searchingWord);

        return searchingWord;
    }

    public ArrayList<TextView> setDynamicButton(ArrayList<KeywordItem> words, LinearLayout location, boolean clickable){
        ArrayList<TextView> list = new ArrayList<TextView>();
        for(int i = 0; i < words.size(); i++) {
            list.add(setDynamicButton(words.get(i), location, clickable));
        }
        return list;
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
