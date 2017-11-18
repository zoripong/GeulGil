package com.five.high.emirim.geulgil.Control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.five.high.emirim.geulgil.Activity.SearchActivity;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-08.
 */

public class DynamicButtonManager {
    private final int SEARCH_KEYWORD_IN_RESULT = 2;
    private final int DETAIL_VIEW_ACTIVITY = 3;

    private int MEAN_ID = 1;
    private int SEARCHING_WORD_ID = 0x8000;
    Context mContext;
    LinearLayout mRootLayout;
    Activity nowActivity;

    ArrayList<TextView> textViews;

    public DynamicButtonManager(Context context, LinearLayout root) {
        mContext = context;
        mRootLayout = root;
        textViews = new ArrayList<>();
    }

    public DynamicButtonManager(Context context, LinearLayout root, Activity nowActivity){
        this(context, root);
        this.nowActivity = nowActivity;
        textViews = new ArrayList<>();
    }

    private TextView setDynamicButton(final KeywordItem word, final LinearLayout location, int clickType){
        Log.e("DYNAMIC BUTTON", "SET OK");

        final TextView searchingWord = new TextView(mContext);
        searchingWord.setText(word.getWord());

        searchingWord.setId(SEARCHING_WORD_ID++);

        customizingBackground(searchingWord, word.isMean());

        location.addView(searchingWord);


        switch (clickType){
            case SEARCH_KEYWORD_IN_RESULT:
                searchingWord.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(nowActivity, SearchActivity.class);
                        nowActivity.startActivity(intent);
                        nowActivity.finish();
                    }
                });
                break;
            case DETAIL_VIEW_ACTIVITY:
                searchingWord.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ConnectApi connectApi = new ConnectApi(mContext, nowActivity);
                        connectApi.getDetailRecord(word.getWord(), 2);
                    }
                });
                break;
            default:
                break;
        }
        return searchingWord;
    }

    public ArrayList<TextView> setDynamicButton(ArrayList<KeywordItem> words, LinearLayout location, int clickType){
        location.removeAllViews();
        ArrayList<TextView> list = new ArrayList<TextView>();
        for(int i = 0; i < words.size(); i++) {
            if(clickType == 4 && i == 4){
                TextView textView = new TextView(mContext);
                textView.setText("+ "+ String.valueOf(words.size()-4));
                textView.setTextSize(12);
                location.addView(textView);
                break;
            }
            list.add(setDynamicButton(words.get(i), location, clickType));
        }
        Log.e("List", list.size()+"개");
        return list;
    }



    public void convertStringToModel(ArrayList<String> keywords, LinearLayout location, boolean isMean){
        Log.e("String To Model", keywords.toString()+"/"+isMean);
        ArrayList<KeywordItem> keywordItems = new ArrayList<KeywordItem>();
        for(int i = 0; i<keywords.size(); i++){
            keywordItems.add(new KeywordItem(keywords.get(i), isMean));
        }
        Log.e("KeywordItems", keywords.size()+"개 / "+ keywordItems.size()+"개");
        setDynamicButton(keywordItems, location, 3);
    }

    private void customizingBackground(TextView word, boolean isMean){
        String strColor;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT    , convertDpToPixel(25));
        params.setMargins(convertDpToPixel((float)2.5), 0, convertDpToPixel((float)2.5), 0); // left top right bottom
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
        location.removeAllViews();
        ArrayList<WordItem> list = words.getWordItems();
        textViews.clear();
        for(int i = 0; i<list.size(); i++){
            setMeanText(i, list.get(i), location, meanLocation, similarLocation);
        }

        setTextColor(0);
    }

    private void setTextColor(int selectPosition){
        Log.e("ㅁ니ㅏㅇ럼;럼ㄴㅇㄹ", String.valueOf(selectPosition));

        for(int i = 0; i<textViews.size(); i++){
            if(i == selectPosition)
                textViews.get(i).setTextColor(Color.parseColor("#000000"));
            else
                textViews.get(i).setTextColor(Color.parseColor("#acacac"));
        }
    }
    private void setMeanText(final int position, final WordItem word, LinearLayout location, final LinearLayout mMeanLocation, final LinearLayout mSimilarLocation){

        TextView meanText = new TextView(mContext);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, convertDpToPixel((float)2.5), 0, convertDpToPixel((float)2.5)); // left top right bottom
        meanText.setLayoutParams(params);

        meanText.setText(MEAN_ID+". "+word.getMean());
        meanText.setId(MEAN_ID++);

        customizingBackground(meanText);
        location.addView(meanText);

        meanText.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // TODO: 2017-11-19 저장
                    convertStringToModel(word.getMeankeyword(), mMeanLocation, true);
                    convertStringToModel(word.getSimilarkeyword(), mSimilarLocation, false);
                    setTextColor(position);
                }
            });

        textViews.add(meanText);

    }

    private void customizingBackground(TextView mean){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, convertDpToPixel(5), 0, convertDpToPixel(5)); // left top right bottom
        mean.setLayoutParams(params);
        mean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        mean.setTextColor(Color.GRAY);

    }

}
