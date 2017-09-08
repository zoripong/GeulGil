package com.five.high.emirim.geulgil.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-19.
 */

public class RecyclerSetter {
    ArrayList<WordItem> items;
    RecyclerAdapter adapter;
//    WordItem[] item;
    String mWord[]; // 즐겨찾는 단어
    private final int STAR_SIZE = 3; // 즐찾 단어 개수

    Context context;
    ControlData controlData;

    public RecyclerSetter(Context context){
        this.context = context;
        controlData = new ControlData(context);
    }

    //리사이클러 카드뷰 세팅
    public boolean setRecyclerCardView(RecyclerView recyclerView, Context context, HashSet<WordItem> hashSet){
        // TODO
        /*
        items = new ArrayList<WordItem>();
        mWord = new String[hashSet.size()];

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Iterator<String> iterator = hashSet.iterator();

        while(iterator.hasNext()) {
            String word = iterator.next();
            String keyword[] = new String[1];
            keyword[0] = word;
            items.add(new WordItem(word, word, word, word,keyword, keyword, 1));
        }

//        item = new WordItem[hashSet.size()];

        /*
        *
        *

        while(iterator.hasNext())
            items.add(controlData.getWordItem(iterator.next()));

        *
        *
        */

        /*

//        if(items.get(0).getmWord().equals(" ")||hashSet.size() == 0) {
        if(hashSet.size() == 0){
////            Snackbar.make(context, "ITPANGPANG", 10000).show();
            Toast.makeText(context, "찾고자 하는 단어 없습니다.", Toast.LENGTH_LONG).show();
            return false ;
        }
////
//        for (int i = 0; i < hashSet.size(); i++) {
//            items.add(item[i]);
//        }


        recyclerView.setAdapter(new RecyclerAdapter(context, items, R.layout.content_main));
        return true;
*/
        items = new ArrayList<WordItem>();
        mWord = new String[hashSet.size()];

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Iterator<WordItem> iterator = hashSet.iterator();

        while(iterator.hasNext()) {
            items.add(iterator.next());
        }

        adapter = new RecyclerAdapter(context, items, R.layout.content_main);
        recyclerView.setAdapter(adapter);
        return true;

    }


    public RecyclerAdapter getAdapter() {
        return adapter;
    }
}

