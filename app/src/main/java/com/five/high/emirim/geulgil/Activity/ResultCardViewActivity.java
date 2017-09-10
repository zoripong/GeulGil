package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.five.high.emirim.geulgil.Adapter.DynamicButtonManager;
import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class ResultCardViewActivity extends AppCompatActivity {
    private final String SEARCHING_WORDS = "searching word";
    private final String RESULT_WORDS = "result word";

    RecyclerView recyclerView;
    RecyclerSetter recyclerSetter;

    ArrayList<SearchingWord> mSearchingWordSet;
    HashSet<WordItem> mResultWordSet;

    DynamicButtonManager dynamicButtonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cardview);

        Intent intent = getIntent();
        mSearchingWordSet = (ArrayList<SearchingWord>) intent.getSerializableExtra(SEARCHING_WORDS);
        mResultWordSet = (HashSet<WordItem>) intent.getSerializableExtra(RESULT_WORDS);

        ImageView searchButton = (ImageView) findViewById(R.id.iv_searchBtn);

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), (LinearLayout) findViewById(R.id.root));
        dynamicButtonManager.setDynamicButton(mSearchingWordSet, (LinearLayout) findViewById(R.id.searched_words), false);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);
        recyclerSetter.setRecyclerCardView(recyclerView, this, mResultWordSet);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultCardViewActivity.this, SearchActivity.class);
                intent.putExtra(SEARCHING_WORDS, mSearchingWordSet);
                intent.putExtra(RESULT_WORDS, mResultWordSet);
                startActivity(intent);
            }
        });
    }
}
