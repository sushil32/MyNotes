package com.krishna.mynotes;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sushil on 11/02/17.
 */

public class General {

    public static Realm realmconfig(Context context){
        RealmConfiguration config = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm myRealm = Realm.getInstance(config);
        return myRealm;
    }
}
