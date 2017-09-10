package com.five.high.emirim.geulgil.Adapter;

/**
 * Created by 유리 on 2017-06-16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Activity.DetailViewActivity;
import com.five.high.emirim.geulgil.Activity.MainActivity;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.List;

// TODO: 2017-09-10 : FLOWLayout , 동음이의어 카드,, 밍..밍..밍... !

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final String SELECT_WORD = "selectedWord";
    Context context;
    List<WordItem> items;

    public RecyclerAdapter(Context context, List<WordItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WordItem item = items.get(position);

        holder.mTvWord.setText(item.getmWord());
        holder.mTvMean.setText(item.getmMean());

        String [] mean = item.getmMeanKeyword();
        String [] similar = item.getmSimilarKeyword();

        ArrayList<KeywordItem> keywords = new ArrayList<KeywordItem>();
        DynamicButtonManager dynamicButtonManager = new DynamicButtonManager(context, holder.root);

        for(int i = 0; i<mean.length; i++)
            keywords.add(new KeywordItem(mean[i], true));

        for(int i = 0; i<similar.length; i++)
            keywords.add(new KeywordItem(similar[i], false));

        dynamicButtonManager.setDynamicButton(keywords, holder.mKeywordLocation ,false);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailViewActivity.class);
                intent.putExtra(SELECT_WORD, items.get(position));
                v.getContext().startActivity(intent);

            }
        });

        holder.mXbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                WordItem itemLabel = items.get(position);
                items.remove(position);
                if(items.size()==0){
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                    Toast.makeText(context, "카드를 모두 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,items.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        CardView cardview;
        TextView mTvWord;
        TextView mTvMean;
        LinearLayout mKeywordLocation;
        ImageView mXbutton;

        public ViewHolder(View itemView) {
            super(itemView);
            root = (LinearLayout)itemView.findViewById(R.id.root);
            mTvWord = (TextView) itemView.findViewById(R.id.tv_word);
            mTvMean = (TextView) itemView.findViewById(R.id.tv_mean);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            mKeywordLocation = (LinearLayout)itemView.findViewById(R.id.keywords_location);
            mXbutton = (ImageView) itemView.findViewById(R.id.x_button);
        }
    }

}