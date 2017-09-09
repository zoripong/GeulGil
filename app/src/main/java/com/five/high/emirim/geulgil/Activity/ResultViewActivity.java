package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

public class ResultViewActivity extends AppCompatActivity {
    private final String SELECT_WORD = "selectedWord";

    FloatingActionButton mFab;
    TextView mWord;
    TextView mPart;
    TextView mMean;
    WordItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        mItem = getIntent().getParcelableExtra(SELECT_WORD);
        init();

        mWord.setText(mItem.getmWord());
        mPart.setText("["+mItem.getmPart()+"]");
        mMean.setText(mItem.getmMean());

        // TODO: 2017-09-08 DYNAMIC BUTTON
    }
    private void init(){
        mFab = (FloatingActionButton) findViewById(R.id.fabLayout).findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 여부 파악
                Intent intent = new Intent(ResultViewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        mWord = (TextView)findViewById(R.id.tv_word);
        mPart = (TextView)findViewById(R.id.tv_position);
        mMean = (TextView)findViewById(R.id.tv_mean);

    }
}
