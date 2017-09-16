package com.five.high.emirim.geulgil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Adapter.CardRecyclerSetter;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.M;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

// // TODO: 2017-09-16  옆에 잘리는 다이나믹 키워드 수정
public class ResultCardViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardRecyclerSetter cardRecyclerSetter;

//    ArrayList<KeywordItem> mKeywordItemList;
    HashSet<SameSounds> mShowItemSet;

    DynamicButtonManager dynamicButtonManager;
    TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cardview);


        LinearLayout searchLocation = (LinearLayout)findViewById(R.id.search_location);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);

        countText = (TextView) findViewById(R.id.count);

        dynamicButtonManager = new DynamicButtonManager(ResultCardViewActivity.this, (LinearLayout) findViewById(R.id.root), ResultCardViewActivity.this);
        // // TODO: 2017-09-16 NullPointException
        Log.e("M.mKeywordItem", M.mKeywordItem.toString());
        dynamicButtonManager.setDynamicButton(M.mKeywordItem, (LinearLayout) findViewById(R.id.searched_words), 2);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        cardRecyclerSetter = new CardRecyclerSetter(this);

        if(checkBox.isChecked())
            cardRecyclerSetter.setRecyclerCardView(recyclerView, crossSet());
        else
            cardRecyclerSetter.setRecyclerCardView(recyclerView, convertListToSet());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    cardRecyclerSetter.setRecyclerCardView(recyclerView, crossSet());
                else
                    cardRecyclerSetter.setRecyclerCardView(recyclerView, convertListToSet());

            }
        });
        searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultCardViewActivity.this, SearchActivity.class);
//                intent.putExtra(SEARCHING_WORDS, mKeywordItemList);
                startActivity(intent);
                finish();
            }
        });

    }
    private HashSet<SameSounds> convertListToSet(){
        ArrayList<HashSet<SameSounds>> list = M.mResult;

        Log.e("NULL", M.mResult+"?");

        HashSet<SameSounds> show = new HashSet<SameSounds>();

        for(int i = 0; i<list.size(); i++){
            Iterator<SameSounds> iterator = list.get(i).iterator();
            while(iterator.hasNext())
                show.add(iterator.next());
        }

        Log.e("Set Size", show.size()+"!");

        countText.setText(show.size()+" 개");

        return show;
    }

    private HashSet<SameSounds> crossSet(){

        ArrayList<HashSet<SameSounds>> list = M.mResult;
        HashSet<SameSounds> newSet = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            HashSet<SameSounds> compareSet = list.get(i);
            HashSet<SameSounds> tempSet = new HashSet<SameSounds>();

            Iterator<SameSounds> newIterator = newSet.iterator();
            while (newIterator.hasNext()) {
                Iterator<SameSounds> compareIterator = compareSet.iterator();
                SameSounds newSameSound = newIterator.next();
                while (compareIterator.hasNext()) {
                    SameSounds compareSameSound = compareIterator.next();
                    if (newSameSound.getId().equals(compareSameSound.getId())) {
                        tempSet.add(newSameSound);
                    }
                }
            }
            newSet = tempSet;
        }

        return newSet;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultCardViewActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
