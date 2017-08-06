package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.R;

import java.util.HashSet;

public class SearchCardviewActivity extends AppCompatActivity implements View.OnClickListener {
    private final String SEARCHING_WORD_KEY = "searchingWord";

    RecyclerView recyclerView;
    RecyclerSetter recyclerSetter;

    HashSet<String> mSearchingWordSet;
    ControlData controlData;

    String mSearchingWord;

    ImageView mIvSearch;
    ImageView mIvSimilarOrMean;
    EditText mEtSearchBox;

    Drawable mDaSimilar;
    Drawable mDaMean;

    boolean wordExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cardview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바 start
        setSupportActionBar(toolbar);

        //DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState(); //툴바 end
        //

        mIvSearch = (ImageView)findViewById(R.id.iv_searchBtn);
        mIvSimilarOrMean = (ImageView)findViewById(R.id.iv_similarOrMean);
        mEtSearchBox = (EditText)findViewById(R.id.et_searchBox);

        mDaSimilar = getResources().getDrawable(R.drawable.similar);
        mDaMean = getResources().getDrawable(R.drawable.meaning);

        mIvSearch.setOnClickListener(this);
        mIvSimilarOrMean.setOnClickListener(this);

        Intent intent = getIntent();
        mSearchingWord = intent.getExtras().getString(SEARCHING_WORD_KEY);

        // 검색 중인 단어들의 집합
        mSearchingWordSet = new HashSet<String>();   // 중복값 제외해줌 + 순서 X
        mSearchingWordSet.add(mSearchingWord);

        controlData = new ControlData(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);

        wordExist = recyclerSetter.setRecyclerCardView(recyclerView, this, mSearchingWordSet);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_similarOrMean:
                M.sSorM = !M.sSorM; // True -> False False -> Mean
                if(M.sSorM == true)
                    mIvSimilarOrMean.setImageDrawable(mDaSimilar);
                else
                    mIvSimilarOrMean.setImageDrawable(mDaMean);
                break;

            case R.id.iv_searchBtn:
                // TODO : 검색 엔진 다시 만들기
                if(controlData.getWordItem(mSearchingWord) == null) {
                    wordExist = false;
                    Toast.makeText(v.getContext(), "단어 검색 중 문제가 발생했습니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    mSearchingWordSet.add(mEtSearchBox.getText().toString());
//                    addSimilarWord(mSearchingWord);
                }
                mEtSearchBox.setText("");
                wordExist = recyclerSetter.setRecyclerCardView(recyclerView, this, mSearchingWordSet);

                break;
        }
    }

    // TODO : 대기
    /*
    // 유사어를 검색어에 추가시킴.. 어라,, 이거 안돼네,, ㅋ
    private void addSimilarWord(String mSearchingWord) {
        String result[] = controlData.parsingTheData(controlData.getData(mSearchingWord));
        Log.e("NOEXIST", result[0]);
        String similar[] = result[0].split(",");
        for(int i = 0; i<similar.length; i++)
            mSearchingWordSet.add(similar[i]);
    }
    */
}
