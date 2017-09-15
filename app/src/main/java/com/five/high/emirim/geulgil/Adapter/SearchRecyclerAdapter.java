package com.five.high.emirim.geulgil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Activity.DetailViewActivity;
import com.five.high.emirim.geulgil.Control.ConnectApi;
import com.five.high.emirim.geulgil.Control.SharedPreferencesManager;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.SearchRecordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;

/**
 * Created by 유리 on 2017-09-14.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.RecordViewHolder> {
    private final String SELECT_WORD = "selectedWord";

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
                    Toast.makeText(context, "검색기록을 모두 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                notifyItemRemoved(finalPosition);
                notifyItemRangeChanged(finalPosition, items.size());
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ConnectApi connectApi = new ConnectApi(context);
                // TODO: 2017-09-14 검색기록과 detail 연결

                SameSounds sameSounds = connectApi.getDetailRecord(String.valueOf(holder.tvWord.getText()));
//                ArrayList<WordItem> list = new ArrayList<WordItem>();
//                ArrayList<String> li = new ArrayList<String>();
//                li.add("라면");
//                li.add("먹고");
//                li.add("싶다");
//                list.add(new WordItem(1, "사랑", "사랑한다", "명사",li,li,0 ));
//                SameSounds sameSounds = new SameSounds("사랑",list);
                Intent intent = new Intent(context, DetailViewActivity.class);
                intent.putExtra(SELECT_WORD, sameSounds);
                context.startActivity(intent);
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


