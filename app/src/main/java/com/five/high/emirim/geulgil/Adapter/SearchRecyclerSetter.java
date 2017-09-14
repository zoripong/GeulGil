package com.five.high.emirim.geulgil.Adapter;

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

    public SearchRecyclerSetter(Context context) {
        this.context = context;
    }

    public boolean setRecyclerCardView(RecyclerView recyclerView){

        items = new ArrayList<SearchRecordItem>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new SearchRecyclerAdapter(context);
        recyclerView.setAdapter(adapter);
        return true;

    }

}
