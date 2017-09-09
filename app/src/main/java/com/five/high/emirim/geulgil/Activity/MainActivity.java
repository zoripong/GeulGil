package com.five.high.emirim.geulgil.Activity;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Adapter.SearchBarManager;
import com.five.high.emirim.geulgil.Adapter.SoftKeyboard;
import com.five.high.emirim.geulgil.R;

public class MainActivity extends AppCompatActivity{
    private final String SEARCHING_WORD_KEY = "searchingWord";
    private final String SEARCHING_WORD_TYPE = "wordtype";

    SearchBarManager manager;
    ImageView mIgLogo;

    InputMethodManager im;
    SoftKeyboard softKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new SearchBarManager(MainActivity.this, this);
        mIgLogo = (ImageView)findViewById(R.id.iv_logo);

        manager.findViewId();

        getWindow().setBackgroundDrawableResource(R.drawable.backgroundimg);

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
                        mIgLogo.setVisibility(View.VISIBLE);
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
                        mIgLogo.setVisibility(View.INVISIBLE);
                    }


                });
            }
        });


        manager.getmSearchButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String word = manager.getmSearchBar().getText().toString();
                if(!word.equals("")) {
                    Intent intent = new Intent(MainActivity.this, SearchCardviewActivity.class);
                    intent.putExtra(SEARCHING_WORD_KEY, word);
                    intent.putExtra(SEARCHING_WORD_TYPE, manager.isMeanKeyword());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "검색 단어를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
