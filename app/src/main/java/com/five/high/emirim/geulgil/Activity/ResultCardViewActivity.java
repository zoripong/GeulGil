package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Adapter.CardRecyclerSetter;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


// TODO: 2017-09-10 : 키워드 단어 결과 연결,,!
// TODO: 2017-09-14 : 결과 내 재검색과 검색 키워드 연결 매칭
public class ResultCardViewActivity extends AppCompatActivity {
    private final String SEARCHING_WORDS = "searching word";

    RecyclerView recyclerView;
    CardRecyclerSetter cardRecyclerSetter;

    ArrayList<KeywordItem> mKeywordItemList;
    HashSet<SameSounds> mShowItemSet;

    DynamicButtonManager dynamicButtonManager;
    TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cardview);

        Intent intent = getIntent();
        mKeywordItemList = (ArrayList<KeywordItem>) intent.getSerializableExtra(SEARCHING_WORDS);

        ImageView searchButton = (ImageView) findViewById(R.id.iv_searchBtn);
        countText = (TextView) findViewById(R.id.count);

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), (LinearLayout) findViewById(R.id.root));
        dynamicButtonManager.setDynamicButton(mKeywordItemList, (LinearLayout) findViewById(R.id.searched_words), false);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        cardRecyclerSetter = new CardRecyclerSetter(this);

        //체크 false
        cardRecyclerSetter.setRecyclerCardView(recyclerView, convertListToSet());

        //체크 true
//        cardRecyclerSetter.setRecyclerCardView(recyclerView, crossSet());
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultCardViewActivity.this, SearchActivity.class);
                intent.putExtra(SEARCHING_WORDS, mKeywordItemList);
                startActivity(intent);
                finish();
            }
        });

    }
    private HashSet<SameSounds> convertListToSet(){
        ArrayList<HashSet<SameSounds>> list = M.mResult;

        Log.e("NULL", M.mResult+"?");

        HashSet<SameSounds> show = new HashSet<SameSounds>();

        for(int i = 0; i<list.size(); i++){
            Iterator<SameSounds> iterator = list.get(i).iterator();
            while(iterator.hasNext())
                show.add(iterator.next());
        }

        Log.e("Set Size", show.size()+"!");

        countText.setText(show.size()+" 개");

        return show;
    }

    private HashSet<SameSounds> crossSet(){
//        HashSet<SameSounds>  = new HashSet<SameSounds>();

        Iterator<SameSounds> iterator = convertListToSet().iterator();

        HashSet<SameSounds> newSet = new HashSet<>();

        for(int i = 0; i<mKeywordItemList.size(); i++) {
            while (iterator.hasNext()) {
                SameSounds sameSounds = iterator.next();
                ArrayList<WordItem> wordItems = sameSounds.getWordItems();
                for(int j = 0; j<wordItems.size(); j++){
                    if(mKeywordItemList.get(i).isMean() && wordItems.get(j).getMeankeyword().contains(mKeywordItemList.get(i).getWord())) {
                        newSet.add(sameSounds);
                        break;
                    }else if((mKeywordItemList.get(i).isMean() == false) && wordItems.get(j).getSimilarkeyword().contains(mKeywordItemList.get(i).getWord())) {
                        newSet.add(sameSounds);
                        break;
                    }
                }

            }
        }

        return newSet;
    }
}
