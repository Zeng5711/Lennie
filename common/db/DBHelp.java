package com.hxyd.dyt.common.db;

import android.content.Context;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

/**
 * Created by win7 on 2017/2/15.
 */

public class DBHelp {

    private RealmConfiguration mConfiguration;

    private static class SingletonHolder {
        private static final DBHelp INSTANCE = new DBHelp();
    }

    public static final DBHelp getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initDB(Context context) {
        //Realm 数据库初始化
        Realm.init(context);
        RealmConfiguration mConfiguration = new RealmConfiguration.Builder()
//                .name("dyt.realm")
//                .encryptionKey(getKey())
//                .schemaVersion(3)
//                .migration(new MyMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(mConfiguration);
    }

    public Realm getRealm() {
//        Realm.deleteRealm(mConfiguration);
        return Realm.getDefaultInstance();
    }

    /**
     * 64位加密
     *
     * @return
     */
    private byte[] getKey() {
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        return key;
    }
}
