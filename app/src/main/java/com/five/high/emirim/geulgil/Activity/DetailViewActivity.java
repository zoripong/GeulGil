package com.five.high.emirim.geulgil.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

public class DetailViewActivity extends AppCompatActivity {
    private final String SELECT_WORD = "selectedWord";

    TextView mWord;
    TextView mPart;
    TextView mMean;
    WordItem mItem;
    Button mYesButton;
    Button mNoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mItem = (WordItem) getIntent().getSerializableExtra(SELECT_WORD);
        init();

        mWord.setText(mItem.getmWord());
        mPart.setText("["+mItem.getmPart()+"]");
        mMean.setText(mItem.getmMean());

        // TODO: 2017-09-08 DYNAMIC BUTTON
    }
    private void init(){
        mWord = (TextView)findViewById(R.id.tv_word);
        mPart = (TextView)findViewById(R.id.tv_position);
        mMean = (TextView)findViewById(R.id.tv_mean);

    }
    public void onBackPressed() {
        Dialog dialog = new Dialog(DetailViewActivity.this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog);
        dialog.show();

        mYesButton =(Button)dialog.findViewById(R.id.dialog_button_yes);
        mNoButton =(Button)dialog.findViewById(R.id.dialog_button_no);
        mYesButton.setEnabled(true);
        mNoButton.setEnabled(true);

        mYesButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){

                Intent intent =new Intent(DetailViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mNoButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                finish();
            }
        });
    }
}
