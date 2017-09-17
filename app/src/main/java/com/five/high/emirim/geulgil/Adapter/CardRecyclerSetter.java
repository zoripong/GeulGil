package com.five.high.emirim.geulgil.Adapter;

import android.app.Activity;
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
    Activity nowActivity;

    public CardRecyclerSetter(Context context, Activity nowActivity){
        this.context = context;
        this.nowActivity = nowActivity;
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

        adapter = new CardRecyclerAdapter(context, items, nowActivity);
        recyclerView.setAdapter(adapter);
        return true;

    }

}

