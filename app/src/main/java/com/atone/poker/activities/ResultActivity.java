package com.atone.poker.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.adapters.ResultAdapter;
import com.atone.poker.models.Result;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT = "res";
    @BindView(R.id.rvResult)
    RecyclerView rvResult;
    @BindView(R.id.toolbar)
    com.google.android.material.appbar.MaterialToolbar toolbar;

    public static void startActivity(Activity act, ArrayList<Result> result) {
        Intent i = new Intent(act, ResultActivity.class);
        i.putParcelableArrayListExtra(EXTRA_RESULT, result);
        act.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result);
            ButterKnife.bind(this);

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("結果");

            //show back button on toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            ArrayList<Result> result = getIntent().getParcelableArrayListExtra(EXTRA_RESULT);

            ResultAdapter adapter = new ResultAdapter(result);
            rvResult.setLayoutManager(new LinearLayoutManager(this));
            rvResult.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}