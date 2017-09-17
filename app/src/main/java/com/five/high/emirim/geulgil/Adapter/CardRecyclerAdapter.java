package com.five.high.emirim.geulgil.Adapter;

/**
 * Created by 유리 on 2017-06-16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Activity.DetailViewActivity;
import com.five.high.emirim.geulgil.Activity.MainActivity;
import com.five.high.emirim.geulgil.Control.DynamicButtonManager;
import com.five.high.emirim.geulgil.Model.KeywordItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.ArrayList;
import java.util.List;

public class CardRecyclerAdapter extends RecyclerView.Adapter<CardRecyclerAdapter.CardViewHolder> {
    private final String SELECT_WORD = "selectedWord";
    Context context;
    List<SameSounds> items;
    Activity nowActiviy;

    public CardRecyclerAdapter(Context context, List<SameSounds> items) {
        this.context = context;
        this.items = items;
    }

    public CardRecyclerAdapter(Context context, List<SameSounds> items, Activity nowActiviy) {
        this(context, items);
        this.nowActiviy = nowActiviy;
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        final SameSounds item = items.get(position);

        holder.mTvWord.setText(item.getId());

        ArrayList<WordItem> wordItem = item.getWordItems();

        if(item.isSingle()){
            String mean = wordItem.get(0).getMean();
            if(mean.length() >= 30) mean = mean.substring(0, 30)+"...";
            holder.mTvMean.setText(mean);

            ArrayList<String> meankeyword = wordItem.get(0).getMeankeyword();
            ArrayList<String> similarkeyword = wordItem.get(0).getSimilarkeyword();

            ArrayList<KeywordItem> keywords = new ArrayList<KeywordItem>();
            DynamicButtonManager dynamicButtonManager = new DynamicButtonManager(context, holder.root);

            for (int i = 0; i < meankeyword.size(); i++)
                keywords.add(new KeywordItem(meankeyword.get(i), true));

            dynamicButtonManager.setDynamicButton(keywords, holder.mMeanKeywordLocation, 4);
            keywords.clear();

            for (int i = 0; i < similarkeyword.size(); i++)
                keywords.add(new KeywordItem(similarkeyword.get(i), false));

            dynamicButtonManager.setDynamicButton(keywords, holder.mSimilarKeywordLocation, 4);
        }else{
            holder.mTvMean.setText("외 " + String.valueOf(item.getWordItems().size()-1)+"개의 결과");
        }

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
                items.remove(position);

                ((TextView)nowActiviy.findViewById(R.id.count)).setText(items.size()+" 개");


                if(items.size()==0){

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                    Toast.makeText(context, "카드를 모두 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                }
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        CardView cardview;
        TextView mTvWord;
        TextView mTvMean;
        LinearLayout mMeanKeywordLocation;
        LinearLayout mSimilarKeywordLocation;
        LinearLayout mXbutton;

        public CardViewHolder(View itemView) {
            super(itemView);
            root = (LinearLayout)itemView.findViewById(R.id.root);
            mTvWord = (TextView) itemView.findViewById(R.id.tv_word);
            mTvMean = (TextView) itemView.findViewById(R.id.tv_mean);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            mMeanKeywordLocation = (LinearLayout)itemView.findViewById(R.id.mean_keywords_location);
            mSimilarKeywordLocation = (LinearLayout)itemView.findViewById(R.id.similar_keywords_location);
            mXbutton = (LinearLayout) itemView.findViewById(R.id.x_button);
        }
    }

}