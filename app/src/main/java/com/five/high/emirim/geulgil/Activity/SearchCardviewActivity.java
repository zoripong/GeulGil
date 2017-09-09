package com.five.high.emirim.geulgil.Activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.DynamicButtonManager;
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

    private final String SEARCHING_WORD_KEY = "searchingWord";
    private final String SEARCHING_WORD_TYPE = "wordtype";

    private FloatingActionButton mFab;

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


    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Context mContext;
    LayoutInflater inflater;


    DynamicButtonManager dynamicButtonManager;
    LinearLayout mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cardview);

        //TODO : 여부파악 (!)
        mContext = getApplicationContext();
        inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.)

        manager = new SearchBarManager(SearchCardviewActivity.this, this);
        manager.findViewId();

        mFab = (FloatingActionButton)findViewById(R.id.fabLayout).findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: 여부 파악

                Intent intent = new Intent(SearchCardviewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

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
                        getSupportActionBar().show();
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
                        getSupportActionBar().hide();
                    }


                });
            }
        });

        manager.getmSearchBar().setHint("지금 생각나는 그거.. 그거 있잖아요!");

        manager.getmSearchButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                //TODO
//                Toast.makeText(getApplication(), "검색 버튼", Toast.LENGTH_SHORT).show();
                reLoading(new SearchingWord(manager.getmSearchBar().getText().toString(), manager.isMeanKeyword()));
//                searchingWord
                im.hideSoftInputFromWindow(manager.getmSearchBar().getWindowToken(),0);
                manager.getmSearchBar().setText("");
                manager.getmSearchBar().clearFocus();
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

        dynamicButtonManager = new DynamicButtonManager(mContext, manager.getmRootLayout());
        mLocation = (LinearLayout)findViewById(R.id.searched_words);

        dynamicButtonManager.setDynamicButton(word, mLocation, false);

        mResultWordSet = new HashSet<WordItem>();
        control = new ControlData();

        // 단어의 결과값을 Hash에 add를 요청
        mResultWordSet = control.searchingWord(mResultWordSet, word);

        if(M.isNull == true){
            Intent backIntent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backIntent);
            Toast.makeText(getApplicationContext(), "원하신 검색 결과가 없습니다! 검색어를 확인해주세요!", Toast.LENGTH_SHORT).show();
            // TODO 전환시 애니메이션
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);

        isExist = recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);
    }

    private void reLoading(SearchingWord word) {
        dynamicButtonManager.setDynamicButton(word, mLocation, true);
        mSearchingWordSet.add(word);
        recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);

    }



    //TODO : 플로팅버튼

    // 수정사항 -> 카드뷰 삭제버튼, 텍스트뷰 커스텀
}
