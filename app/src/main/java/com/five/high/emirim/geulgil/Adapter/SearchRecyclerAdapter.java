package com.five.high.emirim.geulgil.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-14.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.RecordViewHolder> {
    Context context;
    ArrayList<SearchRecordItem> items;
    SharedPreferencesManager sharedPreferencesManager;

    public SearchRecyclerAdapter(Context context){
        this.context = context;
        sharedPreferencesManager = new SharedPreferencesManager();
        items = sharedPreferencesManager.loadSharedPreferencesLogList(context);
    }
    @Override
    public SearchRecyclerAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordview, parent, false);
        return new RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {

        // TODO: 2017-09-14 검색기록과 detail 연결

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
                    Toast.makeText(context, "검색기록을 모두 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                notifyItemRemoved(finalPosition);
                notifyItemRangeChanged(finalPosition, items.size());
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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


