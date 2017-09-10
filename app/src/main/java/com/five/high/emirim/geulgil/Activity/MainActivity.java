package com.five.high.emirim.geulgil.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.SearchingWord;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity{
    private final String SEARCHING_WORDS = "searching word";
    private final String RESULT_WORDS = "result word";

    EditText mEditText;

    private boolean isMean = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.drawable.backgroundimg);

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
                    HashSet<WordItem> resultWordSet = new HashSet<WordItem>();
                    ControlData control = new ControlData();
                    resultWordSet = control.searchingWord(resultWordSet, searchingWord);

                    if(M.isNull == true){
                        Toast.makeText(MainActivity.this, "검색 결과가 없습니다:( 다른 검색어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }else{
                        ArrayList<SearchingWord> searchingWords = new ArrayList<SearchingWord>();
                        searchingWords.add(searchingWord);

                        Intent intent = new Intent(MainActivity.this, ResultCardViewActivity.class);
                        intent.putExtra(SEARCHING_WORDS, searchingWords);
                        intent.putExtra(RESULT_WORDS, resultWordSet);
                        startActivity(intent);
                        finish();

                    }
                }else{
                    Toast.makeText(MainActivity.this, "검색 단어를 입력해주세요!", Toast.LENGTH_SHORT).show();
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
    }

}
