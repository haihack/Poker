package com.atone.poker.presentation.handchecking;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.domain.Hand;
import com.atone.poker.R;
import com.atone.poker.presentation.base.utilities.Validation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class HandAdapter extends RecyclerView.Adapter<HandAdapter.MyViewHolder> {
    private ArrayList<Hand> data;

    public HandAdapter(ArrayList<Hand> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hand, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder vh, int position) {
        try {
            for (int i = 0; i < data.get(position).getCards().length; i++) {

                if (!TextUtils.isEmpty(data.get(position).getCards()[i].getName())) {
                    vh.edtCardList.get(i).removeTextChangedListener(vh.getListener(i));
                    vh.edtCardList.get(i).setText(data.get(position).getCards()[i].getName());
                }

                if (!TextUtils.isEmpty(data.get(position).getCards()[i].getError())) {
                    vh.inputLayoutCardList.get(i).setError(data.get(position).getCards()[i].getError());
                }
            }

            for (int j = 0; j < 5; j++) {
                int finalJ = j;
                TextInputEditText input = vh.edtCardList.get(j);
                TextWatcher listener = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        data.get(position).getCards()[finalJ].setError(null);
                        vh.inputLayoutCardList.get(finalJ).setError("");

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        //remove space
    //                    input.setText(charSequence.toString().replaceAll(" ", ""));
    //                    input.setSelection(input.getText().length());

                        if (!Validation.validateCard(charSequence.toString())) {
                            vh.inputLayoutCardList.get(finalJ).setError(vh.itemView.getContext().getString(R.string.error_card));
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        data.get(position).getCards()[finalJ].setName(editable.toString());
                    }
                };
                input.addTextChangedListener(listener);
                vh.setListener(listener, j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.tvCardName1, R.id.tvCardName2, R.id.tvCardName3, R.id.tvCardName4, R.id.tvCardName5})
        List<TextView> tvCardNameList;
        @BindViews({R.id.edtCard1, R.id.edtCard2, R.id.edtCard3, R.id.edtCard4, R.id.edtCard5})
        List<TextInputEditText> edtCardList;
        @BindViews({R.id.inputLayoutCard1, R.id.inputLayoutCard2, R.id.inputLayoutCard3, R.id.inputLayoutCard4, R.id.inputLayoutCard5})
        List<TextInputLayout> inputLayoutCardList;

        private TextWatcher[] listeners = new TextWatcher[5];

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public TextWatcher getListener(int index) {
            return listeners[index];
        }

        public void setListener(TextWatcher listener, int index) {
            this.listeners[index] = listener;
        }
    }
}
