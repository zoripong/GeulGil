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

import com.five.high.emirim.geulgil.Adapter.DialogManager;
import com.five.high.emirim.geulgil.Adapter.SearchRecyclerSetter;
import com.five.high.emirim.geulgil.Control.ConnectApi;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
    TextView mNotFound;

    SharedPreferencesManager sharedPreferencesManager;
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

        mNotFound = (TextView)findViewById(R.id.not_found);

        isLongClicked = new boolean[mDynamicButtons.size()];

        sharedPreferencesManager = new SharedPreferencesManager();
        if(sharedPreferencesManager.loadSharedPreferencesLogList(SearchActivity.this).size() == 0) {
            mNotFound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }else{
            mNotFound.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }


        //전체 삭제 버튼 리스너
        mRemoveAll = (TextView)findViewById(R.id.all_delete);
        mRemoveAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(sharedPreferencesManager.loadSharedPreferencesLogList(SearchActivity.this).size()>0) {
                    final Dialog dialog = new Dialog(SearchActivity.this, R.style.MyDialog);
                    dialog.setContentView(R.layout.dialog_style2);
                    dialog.show();

                    TextView textView = (TextView) dialog.findViewById(R.id.dialog_text);
                    textView.setText("검색 기록을 모두 삭제하시겠습니까?");

                    Button mYesButton = (Button) dialog.findViewById(R.id.dialog_button_yes);
                    Button mNoButton = (Button) dialog.findViewById(R.id.dialog_button_no);

                    mYesButton.setEnabled(true);
                    mNoButton.setEnabled(true);

                    mYesButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            sharedPreferencesManager.saveSharedPreferencesLogList(getApplicationContext(), new ArrayList<SearchRecordItem>());
                            searchRecyclerSetter.setRecyclerCardView(recyclerView);

                            mNotFound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);

                            dialog.dismiss();

                        }
                    });
                    mNoButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
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

        mEditText.setHint("지금 생각나는 그거 .. 그거 있잖아요!");
//        mEditText.setTextColor();

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String word = mEditText.getText().toString();
                if(checkForm(word)&&!word.toLowerCase().equals("highfive")) {
                    DialogManager dialogManager = new DialogManager(SearchActivity.this);
                    dialogManager.showDialog("영어는 지원하지 않습니다 :(", 1);
                }else if(checkEmpty(word)){
                    DialogManager dialogManager = new DialogManager(SearchActivity.this);
                    dialogManager.showDialog("키워드를 입력해주세요 :(", 1);
                }else{
                    word = word.replace(" ", "");
                    KeywordItem keywordItem = new KeywordItem(word, isMean);
                    ConnectApi connectApi = new ConnectApi(SearchActivity.this, SearchActivity.this);
                    connectApi.getRelativesResult(keywordItem);
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
        // end of include action

        mRemoveButton = (ImageView)findViewById(R.id.remove_keywords);
        mRemoveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO: 2017-09-16 중복코드..
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

    private boolean checkForm(String str) {
        boolean result = Pattern.matches("^[a-zA-Z0-9]*$", str);

        if(str.length() > 0 && result) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkEmpty(String str){
        if(str.length() == 0)
            return true;
        char [] chars = str.toCharArray();
        for(int i = 0; i<str.length(); i++){
            if(chars[i] != ' ')
                return false;
        }
        return true;
    }
}


