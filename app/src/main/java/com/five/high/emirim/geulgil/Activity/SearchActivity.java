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
import com.five.high.emirim.geulgil.Control.ConnectApi;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText mEditText;
    private boolean isMean = true;

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


        mKeywordsLocation = (LinearLayout) findViewById(R.id.searched_words);
        mRootLayout = (LinearLayout) findViewById(R.id.root);

        dynamicButtonManager = new DynamicButtonManager(SearchActivity.this, mRootLayout);
        mDynamicButtons = dynamicButtonManager.setDynamicButton(M.mKeywordItem,mKeywordsLocation, 0);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        searchRecyclerSetter = new SearchRecyclerSetter(SearchActivity.this, SearchActivity.this);
        searchRecyclerSetter.setRecyclerCardView(recyclerView);

        isLongClicked = new boolean[mDynamicButtons.size()];

        //전체 삭제 버튼 리스너
        mRemoveAll = (TextView)findViewById(R.id.all_delete);
        mRemoveAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager();
                sharedPreferencesManager.saveSharedPreferencesLogList(getApplicationContext(), new ArrayList<SearchRecordItem>());
                searchRecyclerSetter.setRecyclerCardView(recyclerView);
            }
        });

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
                        M.mKeywordItem.remove(finalI);
                        M.mResult.remove(finalI);
                        if(M.mKeywordItem.size() == 0)
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
                ConnectApi connectApi = new ConnectApi(SearchActivity.this, SearchActivity.this);
                connectApi.getRelativesResult(keywordItem);
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
                dialog.setContentView(R.layout.dialog_style2);
                dialog.show();

                TextView textView = (TextView)dialog.findViewById(R.id.dialog_text);
                textView.setText("검색 키워드를 모두 삭제하시겠습니까?");
                M.mResult.clear();

                Button mYesButton =(Button)dialog.findViewById(R.id.dialog_button_yes);
                Button mNoButton =(Button)dialog.findViewById(R.id.dialog_button_no);

                mYesButton.setEnabled(true);
                mNoButton.setEnabled(true);

                mYesButton.setOnClickListener(new View.OnClickListener(){
                    public  void onClick(View v){
                        mKeywordsLocation.removeAllViews();
                        M.mKeywordItem.clear();
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
        if(M.mResult.size() == 0){
            M.isShow = true;
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(SearchActivity.this, ResultCardViewActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void changeOrigin(){
        for(int i = 0; i< mDynamicButtons.size(); i++){
            if(isLongClicked[i]) {
                String strColor = null;
                KeywordItem word = M.mKeywordItem.get(i);
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


