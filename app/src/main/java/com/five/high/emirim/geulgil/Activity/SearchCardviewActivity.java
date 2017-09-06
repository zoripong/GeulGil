package com.five.high.emirim.geulgil.Activity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Adapter.SearchBarManager;
import com.five.high.emirim.geulgil.Adapter.SoftKeyboard;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchCardviewActivity extends AppCompatActivity {
    private final String SEARCHING_WORD_KEY = "searchingWord";
    private final String SEARCHING_WORD_TYPE = "wordtype";

    RecyclerView recyclerView;
    RecyclerSetter recyclerSetter;

    ArrayList<String> mSearchingWordSet;
    HashSet<WordItem> mResultWordSet;
    ControlData control;

    boolean isExist = false;

    SearchBarManager manager;
    InputMethodManager im;
    SoftKeyboard softKeyboard;

    private boolean isClicked = false;
    private boolean isMeanKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cardview);

        manager = new SearchBarManager(SearchCardviewActivity.this, this);
        manager.findViewId();
        im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(manager.getmSearchBar().getWindowToken(),0);

        softKeyboard = new SoftKeyboard(manager.getmRootLayout(), im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged(){

            @Override
            public void onSoftKeyboardHide() {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run()
                    {//키보드 내려왔을때
                        manager.showSelectedView();
                    }
                });
            }

            @Override
            public void onSoftKeyboardShow() {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run()
                    {//키보드 올라왔을 때
                        manager.showSelectedView();
                    }


                });
            }
        });


        manager.getmSearchButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO
                String word = manager.getmSearchBar().getText().toString();
            }
        });


        // [ACTION BAR]
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_bar);
        //

        //[Control of Data]
        Intent intent = getIntent();
        String word = intent.getExtras().getString(SEARCHING_WORD_KEY);

        // Activity 변환 시 넘어오는 검색 단어는 1개 뿐
        TextView searchingWord = (TextView)findViewById(R.id.searching_word_01);
        searchingWord.setText(word);

        // 컨트롤로 서버에서 데이터 가져오고 WordItem HashSet에 추가 -> recyclerSetter.setRecyclerCardView
        mSearchingWordSet = new ArrayList<String>();
        mSearchingWordSet.add(word);

        mResultWordSet = new HashSet<WordItem>();
        control = new ControlData();

        // 단어의 결과값을 Hash에 add를 요청
        mResultWordSet = control.searchingWord(mResultWordSet, word);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);

        isExist = recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);
        //

    }

    //// TODO: 2017-09-05 결과내 재검색

    //TODO : 검색 단어 버튼 다이나믹 추가

    //TODO : 플로팅버튼

}
