package com.atone.poker.framework;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.data.HistorySource;
import com.atone.poker.domain.LiveRealmResults;
import com.atone.poker.domain.Result;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.Sort;

public class RealmHistorySourceImp implements HistorySource {
    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void insert(ArrayList<Result> results) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < results.size(); i++) {
                    Result history = results.get(i);
                    Number newId = realm.where(Result.class).max("id");
                    if (newId == null) newId = 0;
                    history.setId(newId.intValue() + 1);
                    history.setTimeStamp(System.currentTimeMillis());
                    realm.insertOrUpdate(history);
                }

            }
        });
    }

    @Override
    public void read(LifecycleOwner owner, Callback callback) {
        LiveRealmResults<Result> history = new LiveRealmResults<>(
                realm.where(Result.class)
                        .sort("id", Sort.DESCENDING)
//                            .limit(LIMIT)
                        .findAllAsync());
        history.observe(owner, results -> {
            callback.onDatabaseChanged(results);
        });

        //https://stackoverflow.com/questions/54652781/how-to-select-a-range-of-items-from-realm-database
//            from = history.get(history.size() - 1).getId() - 1;
//            long to = from - LIMIT;
//            nextRecord = new LiveRealmResults<>(
//                    realm.where(Result.class)
//                            .sort("id", Sort.DESCENDING)
//                            .between("id", to, from)
//                            .lessThan("id", 1000)
//                            .findAllAsync());
//            _10firstRecords.observe(this, results -> {
//                history.addAll(results);
//                adapter.notifyDataSetChanged();
//
//            });
    }

    @Override
    public void delete() {

    }
}
