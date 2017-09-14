package com.five.high.emirim.geulgil.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.SearchRecyclerSetter;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;

// TODO: 2017-09-10 : 검색 기록 .. ㅜ.ㅜ.ㅜㅜ.

public class SearchActivity extends AppCompatActivity {
    private final String SEARCHING_WORDS = "searching word";
    private final String RESULT_WORDS = "result word";

    EditText mEditText;
    private boolean isMean = true;

    ArrayList<KeywordItem> mKeywordItemList;
    HashSet<SameSounds> mResultWordSet;

    DynamicButtonManager dynamicButtonManager;

    LinearLayout mKeywordsLocation;
    LinearLayout mRootLayout;
    TextView mRemoveAll;

    private boolean[] isLongClicked;
    ArrayList<TextView> mDynamicButtons;

    ImageView mRemoveButton;

    SearchRecyclerSetter searchRecyclerSetter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();

        mKeywordItemList = (ArrayList<KeywordItem>) intent.getSerializableExtra(SEARCHING_WORDS);
        mResultWordSet = (HashSet<SameSounds>) intent.getSerializableExtra(RESULT_WORDS);

        mKeywordsLocation = (LinearLayout) findViewById(R.id.searched_words);
        mRootLayout = (LinearLayout) findViewById(R.id.root);

        dynamicButtonManager = new DynamicButtonManager(getApplicationContext(), mRootLayout);
        mDynamicButtons = dynamicButtonManager.setDynamicButton(mKeywordItemList,mKeywordsLocation , true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        searchRecyclerSetter = new SearchRecyclerSetter(this);
        searchRecyclerSetter.setRecyclerCardView(recyclerView);

        isLongClicked = new boolean[mDynamicButtons.size()];

        // TODO: 2017-09-14 전체 삭제 버튼
//        mRemoveAll = (TextView)findViewById(R.id.all_delete);
//        mRemoveAll.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
//                sharedPreferencesManager.saveSharedPreferencesLogList(getApplicationContext(), new ArrayList<SearchRecordItem>());
//                searchRecyclerSetter.setRecyclerCardView(recyclerView);
//            }
//        });
        //TODO : 검색 키워드 삭제시 결과도 변동 되야 함.. 하 .. 스트레스다.. ><

        for(int i = 0; i< mDynamicButtons.size(); i++){
            final TextView keyword = mDynamicButtons.get(i);
            final int finalI = i;
            keyword.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    for(int j = 0; j< mDynamicButtons.size(); j++){
                        changeOrigin();
                    }
                    isLongClicked[finalI] = true;
                    keyword.setBackgroundResource(R.drawable.black_bt);
                    keyword.setTextColor(Color.parseColor("#24FFFFFF"));
                    return true;
                }
            });

            keyword.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(isLongClicked[finalI]){
                        isLongClicked[finalI] = false;
                        mKeywordsLocation.removeView(keyword);
                        mKeywordItemList.remove(finalI);
                        if(mKeywordItemList.size() == 0)
                            mRemoveButton.setVisibility(View.INVISIBLE);

                    }
                    changeOrigin();
                }
            });
        }

        mRootLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeOrigin();
            }
        });


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
                    KeywordItem keywordItem = new KeywordItem(word, isMean);

                    //.. get data
                    ControlData control = new ControlData();
                    mResultWordSet = control.searchingWord(mResultWordSet, keywordItem);

                    if(M.isNull == true){
                        Toast.makeText(SearchActivity.this, "검색 결과가 없습니다:( 다른 검색어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                    }else{
                        mKeywordItemList.add(keywordItem);
                        changeActivity();
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

        mRemoveButton = (ImageView)findViewById(R.id.remove_keywords);
        mRemoveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SearchActivity.this, R.style.MyDialog);
                dialog.setContentView(R.layout.dialog);
                dialog.show();

                TextView textView = (TextView)dialog.findViewById(R.id.dialog_text);
                textView.setText("검색 키워드를 모두 삭제하시겠습니까?");

                Button mYesButton =(Button)dialog.findViewById(R.id.dialog_button_yes);
                Button mNoButton =(Button)dialog.findViewById(R.id.dialog_button_no);

                mYesButton.setEnabled(true);
                mNoButton.setEnabled(true);

                mYesButton.setOnClickListener(new View.OnClickListener(){
                    public  void onClick(View v){
                        mKeywordsLocation.removeAllViews();
                        mKeywordItemList.clear();
                        mRemoveButton.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                mNoButton.setOnClickListener(new View.OnClickListener(){
                    public  void onClick(View v){
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        changeActivity();
    }

    private void changeActivity(){
        if(mKeywordItemList.size() != 0) {
            Intent intent = new Intent(SearchActivity.this, ResultCardViewActivity.class);
            intent.putExtra(SEARCHING_WORDS, mKeywordItemList);
            intent.putExtra(RESULT_WORDS, mResultWordSet);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "키워드를 입력해주세요 :>", Toast.LENGTH_SHORT).show();
        }

    }

    private void changeOrigin(){
        for(int i = 0; i< mDynamicButtons.size(); i++){
            if(isLongClicked[i]) {
                String strColor = null;
                KeywordItem word = mKeywordItemList.get(i);
                isLongClicked[i] = false;
                mDynamicButtons.get(i).setText(word.getWord());
                if(word.isMean()){
                    mDynamicButtons.get(i).setBackgroundResource(R.drawable.keyword_button_mean);
                    strColor = "#FFFFFF";
                }
                else {
                    mDynamicButtons.get(i).setBackgroundResource(R.drawable.keyword_button_similar);
                    strColor = "#1583ff";
                }
                mDynamicButtons.get(i).setTextColor(Color.parseColor(strColor));
            }
        }
    }
}
