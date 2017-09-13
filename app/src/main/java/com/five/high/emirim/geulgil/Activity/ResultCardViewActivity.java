package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;


// TODO: 2017-09-10 : 키워드 단어 결과 연결,,!

public class ResultCardViewActivity extends AppCompatActivity {
    private final String PREFS_TAG = "SharedPrefs";
    private final String PRODUCT_TAG = "MyProduct";

    private final String SEARCHING_WORDS = "searching word";
    private final String RESULT_WORDS = "result word";

    RecyclerView recyclerView;
    RecyclerSetter recyclerSetter;

    ArrayList<KeywordItem> mKeywordItemSet;
    HashSet<SameSounds> mResultWordSet;

    DynamicButtonManager dynamicButtonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cardview);

        Intent intent = getIntent();
        mKeywordItemSet = (ArrayList<KeywordItem>) intent.getSerializableExtra(SEARCHING_WORDS);
        mResultWordSet = (HashSet<SameSounds>) intent.getSerializableExtra(RESULT_WORDS);

        ImageView searchButton = (ImageView) findViewById(R.id.iv_searchBtn);

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), (LinearLayout) findViewById(R.id.root));
        dynamicButtonManager.setDynamicButton(mKeywordItemSet, (LinearLayout) findViewById(R.id.searched_words), false);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);
        recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultCardViewActivity.this, SearchActivity.class);
                intent.putExtra(SEARCHING_WORDS, mKeywordItemSet);
                intent.putExtra(RESULT_WORDS, mResultWordSet);
                startActivity(intent);
                finish();
            }
        });

        // 검색 기록 저장

    }
//    http://findnerd.com/list/view/Save-ArrayList-of-Object-into-Shared-Preferences-in-Android/510?page=10&ppage=3
    //    private List<SearchRecordItem> getDataFromSharedPreferences(){
//        Gson gson = new Gson();
//        List<SearchRecordItem> recordItems = new ArrayList<>();
//        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
//        String jsonPreferences = sharedPref.getString(PRODUCT_TAG, "");
//
//        Type type = new TypeToken<List<SearchRecordItem>>() {}.getType();
//        productFromShared = gson.fromJson(jsonPreferences, type);
//
//        return preferences;
//    }
}
