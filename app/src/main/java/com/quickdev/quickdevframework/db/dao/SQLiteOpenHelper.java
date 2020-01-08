package com.quickdev.quickdevframework.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.quickdev.quickdevframework.bean.TestBean;
import com.quickdev.quickdevframework.db.greendao.DaoMaster;

import org.greenrobot.greendao.database.Database;


public class SQLiteOpenHelper extends DaoMaster.OpenHelper {
    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
//            @Override
//            public void onCreateAllTables(Database db, boolean ifNotExists) {
//                DaoMaster.createAllTables(db, true);
//            }
//
//            @Override
//            public void onDropAllTables(Database db, boolean ifExists) {
//                DaoMaster.dropAllTables(db, true);
//            }
//        }, TestBeanDaoDao.class);
    }
}
