package com.atone.poker.presentation.resultshowing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.domain.Result;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultFragment extends Fragment {

    public static final String EXTRA_RESULT = "res";
    @BindView(R.id.rvResult)
    RecyclerView rvResult;

    public static ResultFragment newInstance(ArrayList<Result> result) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_RESULT, result);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateContentViews();
    }

    protected void updateContentViews() {
        try {
            ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            actionbar.setTitle("結果");
//
//            //show back button on toolbar
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);

            ArrayList<Result> result = getArguments().getParcelableArrayList(EXTRA_RESULT);

            ResultAdapter adapter = new ResultAdapter(result);
            rvResult.setLayoutManager(new LinearLayoutManager(getContext()));
            rvResult.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}