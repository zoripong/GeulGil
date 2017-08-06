package com.five.high.emirim.geulgil.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.CheckBoxManager;
import com.five.high.emirim.geulgil.Adapter.RecyclerSetter;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.R;

import java.util.HashSet;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //검색 키워드 관련 변수 (Using API)
    ImageView mIvSearch;
    ImageView mIvSimilarOrMean;
    ImageView mIvFiltering;
    EditText mEtSearchBox;

    Drawable mDaSimilar;
    Drawable mDaMean;

    // 리사이클러뷰 세팅해주는 class
    RecyclerSetter recyclerSetter;
    RecyclerView recyclerView;

    HashSet<String> hashSet;
    ControlData controlData;

    CheckBoxManager mCheckBoxManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyApplication3 start..
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바 start
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState(); //툴바 end
        //end..

        M.sSorM = true; // 초기값 true(Similar)
        findViewId(); //findViewById 함수 집합
        mCheckBoxManager.setCheckBox();

        mIvSearch.setOnClickListener(this);
        mIvSimilarOrMean.setOnClickListener(this);

        hashSet = new HashSet<String>();

        // 즐찾단어 임의로 넣어줌
        hashSet.add("나"); hashSet.add("자신감"); //arrayList.add("별장");
        recyclerSetter.setRecyclerCardView(recyclerView, this, hashSet);

    }

    private void findViewId(){
        mIvSearch = (ImageView)findViewById(R.id.iv_searchBtn);
        mIvSimilarOrMean = (ImageView)findViewById(R.id.iv_similarOrMean);
        mEtSearchBox = (EditText)findViewById(R.id.et_searchBox);
        mDaSimilar = getResources().getDrawable(R.drawable.similar);
        mDaMean = getResources().getDrawable(R.drawable.meaning);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerSetter = new RecyclerSetter(this);
        controlData = new ControlData(this);
        mCheckBoxManager = new CheckBoxManager();
    }
    @Override
    public void onBackPressed() { //툴바  start (뒤로 가는 버튼)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//툴바 end

    @Override
    public void onClick(View v) {
//        ControlData controlData = new ControlData(v.getContext());
        switch (v.getId()){
            case R.id.iv_similarOrMean:
                M.sSorM = !M.sSorM; // True -> False False -> Mean
                if(M.sSorM == true)
                    mIvSimilarOrMean.setImageDrawable(mDaSimilar);
                else
                    mIvSimilarOrMean.setImageDrawable(mDaMean);
                break;
            case R.id.iv_searchBtn:
                Toast.makeText(this, "검색 버튼", Toast.LENGTH_SHORT).show();
                String searching = mEtSearchBox.getText().toString();
                if(controlData.getWordItem(searching) == null)
                    Toast.makeText(this, "단어 검색에 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
                else
                    controlData.myIntent(this, SearchCardviewActivity.class, searching);
                break;
        }
    }
    public void onCheckboxClicked(View view){
        mCheckBoxManager.onClickCheckBox(view);
    }

}
