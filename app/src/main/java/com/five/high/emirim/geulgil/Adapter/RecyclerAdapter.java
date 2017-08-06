package com.five.high.emirim.geulgil.Adapter;

/**
 * Created by 유리 on 2017-06-16.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.five.high.emirim.geulgil.Activity.ResultViewActivity;
import com.five.high.emirim.geulgil.Control.ControlData;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<WordItem> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<WordItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WordItem item = items.get(position);

        holder.mTvWord.setText(item.getmWord());
        holder.mTvMean.setText(item.getmMean());

        String [] similar = item.getmSimilarKeyword();
        String [] mean = item.getmMeanKeyword();

        StringBuffer addSimilar = new StringBuffer("");
        StringBuffer addMean = new StringBuffer("");

        if(similar != null) {
           for(int i = 0; i<similar.length; i++) {
               if (similar[i].equals("null")) {
                   similar[i] = null;
                   break;
               }
               addSimilar.append(similar[i] + ", ");
           }
        }
        holder.mTvSimilarKeyword.setText(addSimilar);

        if(mean != null) {
            for(int i = 0; i<mean.length; i++) {
                if (mean[i].equals("null")) {
                    mean[i] = null;
                    break;
                }
                addMean.append(mean[i] + ", ");
            }
        }
        holder.mTvMeanKeyword.setText(addMean);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            ControlData controlData = new ControlData(context);
            @Override
            public void onClick(View v) {
                controlData.myIntent(v.getContext(), ResultViewActivity.class, item.getmWord());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView mTvWord;
        TextView mTvMean;
        TextView mTvSimilarKeyword;
        TextView mTvMeanKeyword;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvWord = (TextView) itemView.findViewById(R.id.tv_word);
            mTvMean = (TextView) itemView.findViewById(R.id.tv_mean);
            mTvMeanKeyword = (TextView) itemView.findViewById(R.id.tv_meankeyword);
            mTvSimilarKeyword = (TextView) itemView.findViewById(R.id.tv_similarkeyword);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}