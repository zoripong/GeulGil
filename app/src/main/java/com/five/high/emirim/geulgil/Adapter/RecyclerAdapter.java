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
import android.widget.TextView;
import android.widget.Toast;

import com.five.high.emirim.geulgil.Activity.DetailViewActivity;
import com.five.high.emirim.geulgil.Activity.MainActivity;
import com.five.high.emirim.geulgil.Model.WordItem;
import com.five.high.emirim.geulgil.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final String SELECT_WORD = "selectedWord";
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WordItem item = items.get(position);

        holder.mTvWord.setText(item.getmWord());
        holder.mTvMean.setText(item.getmMean());

        String [] similar = item.getmSimilarKeyword();
        String [] mean = item.getmMeanKeyword();


        // TODO: 2017-09-07 DYNAMIC BUTTON


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
        CardView cardview;
        TextView mTvWord;
        TextView mTvMean;
        TextView mTvSimilarKeyword;
        TextView mTvMeanKeyword;
        ImageView mXbutton;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvWord = (TextView) itemView.findViewById(R.id.tv_word);
            mTvMean = (TextView) itemView.findViewById(R.id.tv_mean);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            mXbutton = (ImageView) itemView.findViewById(R.id.x_button);
        }
    }

}