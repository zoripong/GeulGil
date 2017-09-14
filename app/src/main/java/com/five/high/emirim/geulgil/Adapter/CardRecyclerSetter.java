package com.five.high.emirim.geulgil.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.five.high.emirim.geulgil.Model.SameSounds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by 유리 on 2017-06-19.
 */

public class CardRecyclerSetter {
    ArrayList<SameSounds> items;
    CardRecyclerAdapter adapter;

    Context context;

    public CardRecyclerSetter(Context context){
        this.context = context;
    }

    //리사이클러 카드뷰 세팅
    public boolean setRecyclerCardView(RecyclerView recyclerView, HashSet<SameSounds> hashSet){

        items = new ArrayList<SameSounds>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        Iterator<SameSounds> iterator = hashSet.iterator();

        while(iterator.hasNext()) {
            items.add(iterator.next());
        }

        adapter = new CardRecyclerAdapter(context, items);
        recyclerView.setAdapter(adapter);
        return true;

    }

}

