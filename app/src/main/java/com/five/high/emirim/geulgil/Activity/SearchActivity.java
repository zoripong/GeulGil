package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.DynamicButtonManager;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchActivity extends AppCompatActivity {
    private final String SEARCHING_WORDS = "searching word";
    private final String RESULT_WORDS = "result word";

    EditText mEditText;
    private boolean isMean = true;

    ArrayList<SearchingWord> mSearchingWordSet;
    HashSet<WordItem> mResultWordSet;

    DynamicButtonManager dynamicButtonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mSearchingWordSet = (ArrayList<SearchingWord>) intent.getSerializableExtra(SEARCHING_WORDS);
        mResultWordSet = (HashSet<WordItem>) intent.getSerializableExtra(RESULT_WORDS);

        Log.e("잘 왔 니 ?", mResultWordSet.toString());

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), (LinearLayout) findViewById(R.id.root));
        dynamicButtonManager.setDynamicButton(mSearchingWordSet, (LinearLayout) findViewById(R.id.searched_words), true);

        // include action
        View searchBar = findViewById(R.id.search_box);
        ImageView searchButton = (ImageView)searchBar.findViewById(R.id.iv_searchBtn);
        mEditText = (EditText)searchBar.findViewById(R.id.et_searchBox);
        Spinner spinner = (Spinner)searchBar.findViewById(R.id.spinner);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String word = mEditText.getText().toString();
                if(!word.equals("")) {
                    SearchingWord searchingWord = new SearchingWord(word, isMean);

                    //.. get data
                    ControlData control = new ControlData();
                    mResultWordSet = control.searchingWord(mResultWordSet, searchingWord);

                    if(M.isNull == true){
                        Toast.makeText(SearchActivity.this, "검색 결과가 없습니다:( 다른 검색어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }else{
                        mSearchingWordSet.add(searchingWord);

                        Intent intent = new Intent(SearchActivity.this, ResultCardViewActivity.class);
                        intent.putExtra(SEARCHING_WORDS, mSearchingWordSet);
                        intent.putExtra(RESULT_WORDS, mResultWordSet);
                        startActivity(intent);
                        finish();

                    }
                }else{
                    Toast.makeText(SearchActivity.this, "검색 단어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    isMean = true;
                }else if(position == 1){
                    isMean = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // end of include action

        /*
        View view = findViewById(R.id.search_box);
        mEditText = (EditText)view.findViewById(R.id.et_searchBox);
        ImageView searchButton = (ImageView)view.findViewById(R.id.iv_searchBtn);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String word = mEditText.getText().toString();
                if(!word.equals("")) {
                    Intent intent = new Intent(SearchActivity.this, ResultCardViewActivity.class);
                    intent.putExtra(SEARCHING_WORD_KEY, word);
                    intent.putExtra(SEARCHING_WORD_TYPE, isMean);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SearchActivity.this, "검색 단어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    isMean = true;
                }else if(position == 1){
                    isMean = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }
    // TODO : 다이나믹 검색
}
