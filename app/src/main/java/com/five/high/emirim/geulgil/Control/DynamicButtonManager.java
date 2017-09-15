package com.five.high.emirim.geulgil.Control;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-08.
 */

public class DynamicButtonManager {
    private int MEAN_ID = 1;
    private int SEARCHING_WORD_ID = 0x8000;
    Context mContext;
    LinearLayout mRootLayout;
    Activity nowActivity;

    public DynamicButtonManager(Context context, LinearLayout root) {
        mContext = context;
        mRootLayout = root;
    }

    public DynamicButtonManager(Context context, LinearLayout root, Activity nowActivity){
        this(context, root);
        this.nowActivity = nowActivity;
    }

    private TextView setDynamicButton(final KeywordItem word, final LinearLayout location, boolean clickable){
        Log.e("DYNAMIC BUTTON", "SET OK");

        final TextView searchingWord = new TextView(mContext);
        searchingWord.setText(word.getWord());

        searchingWord.setId(SEARCHING_WORD_ID++);

        customizingBackground(searchingWord, word.isMean());

        location.addView(searchingWord);

        if(clickable){
            searchingWord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ConnectApi connectApi = new ConnectApi(mContext);
                    SameSounds item = connectApi.getDetailRecord(word.getWord());

                    TextView tvWord = (TextView)nowActivity.findViewById(R.id.tv_word);
                    TextView tvPosition = (TextView)nowActivity.findViewById(R.id.tv_position);
                    LinearLayout meanKeywordsLocation = (LinearLayout)nowActivity.findViewById(R.id.keywords_location_mean);
                    LinearLayout similarKeywordsLocation = (LinearLayout) nowActivity.findViewById(R.id.keywords_location_similar);
                    LinearLayout meanLocation = (LinearLayout) nowActivity.findViewById(R.id.mean_location);

                    tvWord.setText(item.getId());
                    tvPosition.setText("[" + item.getWordItems().get(0).getPart() + "]");
                    convertStringToModel(item.getWordItems().get(0).getMeankeyword(), meanKeywordsLocation, true);
                    convertStringToModel(item.getWordItems().get(0).getSimilarkeyword(), similarKeywordsLocation, false);
                    setMeanText(item, meanLocation, meanKeywordsLocation, similarKeywordsLocation);

                }
            });
        }
        return searchingWord;
    }

    public ArrayList<TextView> setDynamicButton(ArrayList<KeywordItem> words, LinearLayout location, boolean clickable){
        location.removeAllViews();
        ArrayList<TextView> list = new ArrayList<TextView>();
        for(int i = 0; i < words.size(); i++) {
            list.add(setDynamicButton(words.get(i), location, clickable));
        }
        return list;
    }

    public void convertStringToModel(ArrayList<String> keywords, LinearLayout location, boolean isMean){

        ArrayList<KeywordItem> keywordItems = new ArrayList<KeywordItem>();
        for(int i = 0; i<keywords.size(); i++){
            keywordItems.add(new KeywordItem(keywords.get(i), isMean));
        }
        setDynamicButton(keywordItems, location, true);
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
    public void setMeanText(SameSounds words, LinearLayout location, LinearLayout meanLocation, LinearLayout similarLocation){
        ArrayList<WordItem> list = words.getWordItems();
        for(int i = 0; i<list.size(); i++){
            setMeanText(list.get(i), location, meanLocation, similarLocation);
        }
    }

    private void setMeanText(final WordItem word, LinearLayout location, final LinearLayout mMeanLocation, final LinearLayout mSimilarLocation){
        TextView meanText = new TextView(mContext);
        meanText.setText(MEAN_ID+". "+word.getMean());
        meanText.setId(MEAN_ID++);
        customizingBackground(meanText);
        location.addView(meanText);

        meanText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    convertStringToModel(word.getMeankeyword(), mMeanLocation, true);
                    convertStringToModel(word.getSimilarkeyword(), mSimilarLocation, false);
                }
            });
    }

    private void customizingBackground(TextView mean){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, convertDpToPixel(5), 0, convertDpToPixel(5)); // left top right bottom
        mean.setLayoutParams(params);
        mean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        mean.setTextColor(Color.BLACK);
    }



}
