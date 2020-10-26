package com.atone.poker.presentation.handchecking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.domain.Result;
import com.atone.poker.presentation.base.dialog.LottieDialogFragment;
import com.atone.poker.presentation.base.widgets.SimpleDividerItemDecoration;
import com.atone.poker.presentation.main.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HandCheckingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HandCheckingFragment extends Fragment implements HandChecking.View {

    @BindView(R.id.rvHand)
    RecyclerView rvHand;
    @BindView(R.id.btnAddHand)
    TextView btnAddHand;

    HandAdapter adapter;
    LinearLayoutManager llmanager;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.rootViewHandChecking)
    NestedScrollView rootView;

    private HandCheckingPresenter presenter = new HandCheckingPresenter(this);

    @OnClick(R.id.btnAddHand)
    public void addHand() {
        presenter.addHand();
    }

    @OnClick(R.id.btnSubmit)
    public void submit() {
        showLoading();
        presenter.requestToCheckHand();
    }

    public void openResultScreen(ArrayList<Result> result) {
        ((MainActivity) getActivity()).openResultScreen(result);
    }

    @Override
    public void refreshAdapterAtPosition(int i) {
        adapter.notifyItemChanged(i);
    }

    private LottieDialogFragment loading = new LottieDialogFragment();

    public static HandCheckingFragment newInstance() {
        HandCheckingFragment fragment = new HandCheckingFragment();
        return fragment;
    }

    @Override
    public void showLoading() {
        if (!loading.isAdded()) {
            loading.show(getChildFragmentManager(), "loader");
        }
    }

    @Override
    public void hideLoading() {
        if (loading.isAdded()) {
            loading.dismissAllowingStateLoss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hand_checking, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateContentViews();
    }

    public void updateContentViews() {
        try {
            adapter = new HandAdapter(presenter.getAdapterData());
            llmanager = new LinearLayoutManager(getContext());

            rvHand.addItemDecoration(
                    new SimpleDividerItemDecoration(
                            ContextCompat.getDrawable(getActivity(), R.drawable.divider),
                            false,
                            false));

            rvHand.setLayoutManager(llmanager);
            rvHand.setAdapter(adapter);
            rvHand.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayNewItem(int size) {
        adapter.notifyItemInserted(size - 1);
        rvHand.post(new Runnable() {
            @Override
            public void run() {
                float dy = rvHand.getY() + rvHand.getChildAt(size - 1).getY();
                rootView.smoothScrollTo(0, (int) dy);
            }
        });
    }
}