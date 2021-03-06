package com.five.high.emirim.geulgil.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Control.ConnectApi;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-14.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.RecordViewHolder> {
    Context context;
    Activity nowActivity;

    ArrayList<SearchRecordItem> items;
    SharedPreferencesManager sharedPreferencesManager;

    public SearchRecyclerAdapter(Context context, Activity nowActivity){
        this.context = context;
        this.nowActivity = nowActivity;
        sharedPreferencesManager = new SharedPreferencesManager();
        items = sharedPreferencesManager.loadSharedPreferencesLogList(context);
    }
    @Override
    public SearchRecyclerAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordview, parent, false);
        return new RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, int position) {
        final SearchRecordItem item = items.get(position);
        final int finalPosition = position;

        holder.tvWord.setText(item.getWord());
        holder.tvMean.setText(item.getMean());

        holder.ivDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                items.remove(finalPosition);
                sharedPreferencesManager.saveSharedPreferencesLogList(context, items);
                if(items.size()==0){
                    nowActivity.findViewById(R.id.not_found).setVisibility(View.VISIBLE);
                    nowActivity.findViewById(R.id.recyclerview).setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "검색기록을 모두 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                notifyItemRemoved(finalPosition);
                notifyItemRangeChanged(finalPosition, items.size());
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ConnectApi connectApi = new ConnectApi(context, nowActivity);
                connectApi.getDetailRecord(String.valueOf(holder.tvWord.getText()), 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        TextView tvWord;
        TextView tvMean;
        ImageView ivDeleteButton;

        public RecordViewHolder(View itemView) {
            super(itemView);
            root = (LinearLayout)itemView.findViewById(R.id.root);
            tvWord = (TextView)itemView.findViewById(R.id.tv_word);
            tvMean = (TextView)itemView.findViewById(R.id.tv_mean);
            ivDeleteButton = (ImageView)itemView.findViewById(R.id.iv_delete);
        }
    }


}


