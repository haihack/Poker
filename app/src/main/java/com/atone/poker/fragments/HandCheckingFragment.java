package com.atone.poker.fragments;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.activities.ResultActivity;
import com.atone.poker.adapters.HandAdapter;
import com.atone.poker.adapters.HistoryAdapter;
import com.atone.poker.fragments.dialog.LottieDialogFragment;
import com.atone.poker.models.CardCheckingRequest;
import com.atone.poker.models.CardCheckingResponse;
import com.atone.poker.models.Error;
import com.atone.poker.models.Hand;
import com.atone.poker.models.Result;
import com.atone.poker.network.RestClient;
import com.atone.poker.utilities.AppLogger;
import com.atone.poker.widgets.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HandCheckingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HandCheckingFragment extends Fragment {

    @BindView(R.id.rvHand)
    RecyclerView rvHand;
    @BindView(R.id.btnAddHand)
    TextView btnAddHand;

    ArrayList<Hand> handList;
    HandAdapter adapter;
    LinearLayoutManager llmanager;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.rootView)
    NestedScrollView rootView;

    @OnClick(R.id.btnAddHand)
    public void addHand() {
        handList.add(new Hand());
        adapter.notifyItemInserted(handList.size() - 1);

        rvHand.post(new Runnable() {
            @Override
            public void run() {
                float dy = rvHand.getY() + rvHand.getChildAt(handList.size() - 1).getY();
                rootView.smoothScrollTo(0, (int) dy);
            }
        });
    }


    @OnClick(R.id.btnSubmit)
    public void submit() {
        showLoading();

/*        JSONStringer jsonStringer = null;
        try {
            jsonStringer = new JSONStringer()
                    .object()
                    .key("cards")
                    .array()
                    .value("H1 H13 H12 H11 H19")
                    .value("H9 C9 S9 H2 C2")
                    .value("C13 D12 C11 H8 H7")
                    .endArray()
                    .endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        CardCheckingRequest card = new CardCheckingRequest();
        for (int i = 0; i < handList.size(); i++) {
            card.getCards().add(handList.get(i).getFullHand());
        }

        RestClient.getApiService().requestToCheckCards(card).enqueue(
                new Callback<CardCheckingResponse>() {
                    @Override
                    public void onResponse(Call<CardCheckingResponse> call, Response<CardCheckingResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getError() == null) {
                                try {
                                    ArrayList<Result> result = response.body().getResult();
                                    ResultActivity.startActivity(getActivity(), result);

                                    for (int i = 0; i < result.size(); i++) {
                                        Realm realm = Realm.getDefaultInstance();
                                        int finalI = i;
                                        realm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                Result history = result.get(finalI);
                                                Number newId = realm.where(Result.class).max("id");
                                                if (newId == null) newId = 0;
                                                history.setId(newId.intValue() + 1);
                                                history.setTimeStamp(System.currentTimeMillis());
                                                realm.insertOrUpdate(history);
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                //TODO handling error
                                try {
                                    ArrayList<Error> error = response.body().getError();
                                    for (int i = 0; i < error.size(); i++) {
                                        for (int j = 0; j < handList.size(); j++) {
                                            if (error.get(i).getHand().equalsIgnoreCase(handList.get(j).getFullHand())) {
                                                String errorCardMessage = error.get(i).getMessage();
                                                if (errorCardMessage.contains("重複")) {
                                                    for (int k = 0; k < 5; k++) {
                                                        for (int l = k + 1; l < 5; l++) {
                                                            if (handList.get(j).getCards()[k].getName()
                                                                    .equals(handList.get(j).getCards()[l].getName()) ) {
                                                                handList.get(j).getCards()[k].setError(errorCardMessage);
                                                                handList.get(j).getCards()[l].setError(errorCardMessage);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    int errorCardIndex = Integer.parseInt(errorCardMessage.substring(0, 1)) - 1;
                                                    handList.get(j).getCards()[errorCardIndex].setError(errorCardMessage);
                                                }
                                                adapter.notifyItemChanged(j);
                                            }

                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            hideLoading();

                        }
                    }

                    @Override
                    public void onFailure(Call<CardCheckingResponse> call, Throwable t) {
                        try {
                            AppLogger.e(t.getLocalizedMessage());
                            hideLoading();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private LottieDialogFragment loading = new LottieDialogFragment();

    public static HandCheckingFragment newInstance() {
        HandCheckingFragment fragment = new HandCheckingFragment();
        return fragment;
    }

    public void showLoading() {
        if (!loading.isAdded()) {
            loading.show(getChildFragmentManager(), "loader");
        }
    }

    public void hideLoading() {
        if (loading.isAdded()) {
            loading.dismissAllowingStateLoss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hand_checking, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            handList = new ArrayList<>();
            handList.add(new Hand());
            adapter = new HandAdapter(handList);
            llmanager = new LinearLayoutManager(getContext());

            rvHand.addItemDecoration(
                    new SimpleDividerItemDecoration(
                            ContextCompat.getDrawable(getActivity(), R.drawable.divider),
                            false,
                            false));

//        DividerItemDecoration deco = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        deco.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
//        rvHand.addItemDecoration(deco);

            rvHand.setLayoutManager(llmanager);
            rvHand.setAdapter(adapter);
            rvHand.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}