package com.five.high.emirim.geulgil.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.five.high.emirim.geulgil.Model.SearchRecordItem;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-14.
 */

public class SearchRecyclerSetter {
    ArrayList<SearchRecordItem> items;
    SearchRecyclerAdapter adapter;

    Context context;
    Activity nowActivity;

    public SearchRecyclerSetter(Context context, Activity nowActivity) {
        this.context = context;
        this.nowActivity = nowActivity;
    }

    public boolean setRecyclerCardView(RecyclerView recyclerView){

        items = new ArrayList<SearchRecordItem>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new SearchRecyclerAdapter(context, nowActivity);
        recyclerView.setAdapter(adapter);
        return true;

    }

}
