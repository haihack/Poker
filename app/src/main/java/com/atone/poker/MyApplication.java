package com.atone.poker;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);


    //init Realm
    Realm.init(getApplicationContext());
    RealmConfiguration config =
            new RealmConfiguration.Builder()
                    .name("atone_poker.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
    Realm.setDefaultConfiguration(config);
  }
}