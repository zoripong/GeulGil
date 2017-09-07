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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Adapter.SearchBarManager;
import com.five.high.emirim.geulgil.Adapter.SoftKeyboard;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchCardviewActivity extends AppCompatActivity {
    private final int DYNAMIC_VIEW_ID = 0x8000;

    private final String SEARCHING_WORD_KEY = "searchingWord";
    private final String SEARCHING_WORD_TYPE = "wordtype";

    int reqNum;
    TextView searchingWord;
    private LinearLayout searched_words;

    RecyclerView recyclerView;
    RecyclerSetter recyclerSetter;

    ArrayList<SearchingWord> mSearchingWordSet;
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
//                //TODO
//                Toast.makeText(getApplication(), "검색 버튼", Toast.LENGTH_SHORT).show();
                reLoading(new SearchingWord(manager.getmSearchBar().getText().toString(), manager.isMeanKeyword()));
//                searchingWord
                im.hideSoftInputFromWindow(manager.getmSearchBar().getWindowToken(),0);
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
        SearchingWord word = new SearchingWord(intent.getExtras().getString(SEARCHING_WORD_KEY), intent.getExtras().getBoolean(SEARCHING_WORD_TYPE));

        // Activity 변환 시 넘어오는 검색 단어는 1개 뿐
//        TextView searchingWord = (TextView)findViewById(R.id.searching_word_01);
//        searchingWord.setText(word.getWord());

        // 컨트롤로 서버에서 데이터 가져오고 WordItem HashSet에 추가 -> recyclerSetter.setRecyclerCardView
        mSearchingWordSet = new ArrayList<SearchingWord>();
        mSearchingWordSet.add(word);

        setSearchingWord(word);

        mResultWordSet = new HashSet<WordItem>();
        control = new ControlData();

        // 단어의 결과값을 Hash에 add를 요청
        mResultWordSet = control.searchingWord(mResultWordSet, word);

        if(M.isNull == true){
            Intent backIntent = new Intent(this, MainActivity.class);
            startActivity(backIntent);
            Toast.makeText(getApplicationContext(), "원하신 검색 결과가 없습니다! 검색어를 확인해주세요!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);

        isExist = recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);
        //

    }

    private void reLoading(SearchingWord word) {
        setSearchingWord(word);
        mSearchingWordSet.add(word);
        recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);

    }


    //TODO : 검색 단어 버튼 다이나믹 추가
    private void setSearchingWord(SearchingWord word){
        reqNum = mSearchingWordSet.size();
        searched_words = (LinearLayout) findViewById(R.id.searched_words);
        searched_words.setOrientation(LinearLayout.VERTICAL);

        for(int i = reqNum-1; i < reqNum; i++) {
            searchingWord = new TextView(this);
            searchingWord.setText(word.getWord());
            //int resId = getResources().getIdentifier("btn_select_" + i, "id", getContext().getPackageName());
            searchingWord.setId(DYNAMIC_VIEW_ID + i);
            pushButton(searchingWord);
            searched_words.addView(searchingWord);
        }
    }

    private void pushButton(final TextView searchingWord) {

        searchingWord.setOnClickListener( new View.OnClickListener(){
            public void onClick (View v){
                Toast.makeText(SearchCardviewActivity.this, searchingWord.getText()+" "+searchingWord.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //TODO : 플로팅버튼

}
