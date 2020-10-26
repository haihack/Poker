package com.atone.poker.presentation.resultshowing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.domain.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private List<Result> data;

    public ResultAdapter(ArrayList<Result> result) {
        this.data = result;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        try {
            vh.tvCards.setText(data.get(position).getCard());
            vh.tvHandName.setText(data.get(position).getHand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static
    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvHandName)
        TextView tvHandName;
        @BindView(R.id.tvCards)
        TextView tvCards;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
