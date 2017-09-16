package com.five.high.emirim.geulgil.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Adapter.DialogManager;
import com.five.high.emirim.geulgil.Adapter.PrefManager;
import com.five.high.emirim.geulgil.Control.ConnectApi;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{
    private final String SEARCHING_WORDS = "searching word";

    EditText mEditText;
    PrefManager prefManager;
    private boolean isMean = true;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.drawable.background);

        M.mResult = new ArrayList<HashSet<SameSounds>>();
        M.mKeywordItem = new ArrayList<KeywordItem>();

        View searchBar = findViewById(R.id.search_box);
        ImageView searchButton = (ImageView)searchBar.findViewById(R.id.iv_searchBtn);
        mEditText = (EditText)searchBar.findViewById(R.id.et_searchBox);
        Spinner spinner = (Spinner)searchBar.findViewById(R.id.spinner);

        if(M.isShow)        {
            M.isShow = false;
            DialogManager dialogManager = new DialogManager(MainActivity.this);
            dialogManager.showDialog("검색 키워드가 없습니다 :>", 1);
        }

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String word = mEditText.getText().toString();
                if(!word.equals("")) {
                    KeywordItem keywordItem = new KeywordItem(word, isMean);
                    ConnectApi connectApi = new ConnectApi(MainActivity.this, MainActivity.this);
                    connectApi.getRelativesResult(keywordItem);
                }
                else{
                    DialogManager dialogManager = new DialogManager(MainActivity.this);
                    dialogManager.showDialog("검색 단어를 입력해주세요 :>", 1);
                }


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#787878"));
                if(position == 0){
                    isMean = true;
                }else if(position == 1){
                    isMean = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ImageView question = (ImageView) findViewById(R.id.question_mark);

        question.setImageAlpha(50);
        question.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                prefManager = new PrefManager(getApplicationContext());
                // make first time launch TRUE
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
                finish();

            }
        });
    }

}

