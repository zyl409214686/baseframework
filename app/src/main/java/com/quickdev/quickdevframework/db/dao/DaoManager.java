package com.quickdev.quickdevframework.db.dao;


import android.content.Context;


import com.quickdev.quickdevframework.db.greendao.DaoMaster;
import com.quickdev.quickdevframework.db.greendao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;


public class DaoManager {

    private static final String DB_NAME = "baseframework.db";//数据库名称
    private volatile static DaoManager mDaoManager;//多线程访问
    private static SQLiteOpenHelper mHelper;
    private static DaoMaster mDaomaster;
    private static DaoSession mDaosession;
    private static Context mContext;

    /**
     * 使用单例模式获得操作数据库的对象
     * @return
     */
    public static DaoManager getInstance() {
        return ManagerNested.daoManager;
    }

    /**
     * 使用单例模式获得操作数据库的对象
     * @return
     */
    public static DaoManager getInstance(Context context) {
        mContext = context;
        return ManagerNested.daoManager;
    }

    private static class ManagerNested {
        private static DaoManager daoManager = new DaoManager();
    }

    /**
     * 初始化Context对象
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * 判断数据库是否存在，如果不存在则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (null == mDaomaster || mHelper == null) {
            mHelper = new SQLiteOpenHelper(mContext, DB_NAME, null);
            mDaomaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        return mDaomaster;
    }

    /**
     * 完成对数据库的增删改查
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (null == mDaosession) {
            if (null == mDaomaster) {
                mDaomaster = getDaoMaster();
            }
            mDaosession = mDaomaster.newSession();
        }
        return mDaosession;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     *
     * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    public void closeHelper() {
        if (null != mHelper) {
            mHelper.close();
            mHelper = null;
        }
    }

    public void closeDaoSession() {
        if (null != mDaosession) {
            mDaosession.clear();
            mDaosession = null;
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
        mDaomaster = null;
    }
}
