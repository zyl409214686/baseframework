package com.quickdev.quickdevframework.db.dao;


import android.content.Context;
import android.util.Log;


import com.quickdev.baseframework.utils.LogUtils;
import com.quickdev.quickdevframework.bean.TestBean;
import com.quickdev.quickdevframework.db.greendao.DaoSession;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;


public class BaseDao<T> {
    private final String TAG = "greenDAO";
    public static final boolean DEBUG = true;
    public DaoManager daoManager;
    public DicDaoManager dicDaoManager;
    public DaoSession daoSession;


    public void handleITron(Context context) {
        daoManager = DaoManager.getInstance(context);
        daoSession = daoManager.getDaoSession();
        daoManager.setDebug(DEBUG);
    }

    public void handleDic() {
        dicDaoManager = DicDaoManager.getInstance();
        daoSession = dicDaoManager.getDaoSession();
        dicDaoManager.setDebug(DEBUG);
    }

    /**
     * 插入单个对象
     *
     * @param object
     * @return
     */
    public boolean insertObject(T object) {
        boolean flag = false;
        try {
            flag = daoSession.insertOrReplace(object) != -1 ? true : false;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return flag;
    }

    public boolean insertMultObject(final List<T> objects) {
        return insertMultObject(objects, false);
    }

    /**
     * 插入多个对象，并开启新的线程
     *
     * @param objects
     * @return
     */
    public boolean insertMultObject(final List<T> objects, final boolean isClear) {
        boolean flag = false;
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    if (isClear) {
                        daoSession.deleteAll(TestBean.class);
                    }
                    for (T object : objects) {
                        long rowId = daoSession.insert(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            Log.d(TAG, "insertMultObject - fail,exception:" + e.toString());
        }
        return flag;
    }

    /**
     * 以对象形式进行数据修改，其中必须要知道对象的主键ID
     *
     * @param object
     */
    public void updateObject(T object) {
        if (null == object) {
            return;
        }
        try {
            daoSession.update(object);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @param clss
     */
    public void updateMultObject(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            daoSession.getDao(clss).updateInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        daoSession.update(object);
                    }
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    /**
     * 删除某张数据库表
     *
     * @param clss
     * @return
     */
    public boolean deleteAll(Class clss) {
        boolean flag = false;
        try {
            daoSession.deleteAll(clss);
//            daoSession.deleteAll(clss);
            flag = true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 删除某个对象
     *
     * @param object
     */
    public void deleteObject(T object) {
        try {
            daoSession.delete(object);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
    }

    public boolean deleteObjectWithWhere(String tableName, String where, String... params) {
        boolean flag = false;
        try {
            daoSession.getDatabase().execSQL("DELETE FROM " + tableName + " " + where, params);
            flag = true;
        } catch (Exception e) {
            flag = false;
            LogUtils.e(TAG, e.toString());
        }
        return flag;
    }

    /**
     * 异步批量删除数据
     *
     * @param objects
     * @param clss
     * @return
     */
    public boolean deleteMultObject(final List<T> objects, Class clss) {
        boolean flag = false;
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            daoSession.getDao(clss).deleteInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        daoSession.delete(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
            flag = false;
        }
        return flag;
    }

    /**
     * 获得某张表名
     *
     * @param clss
     * @return
     */
    public String getTablename(Class clss) {
        return daoSession.getDao(clss).getTablename();
    }

    /**
     * 查询某个ID的对象是否存在
     * @param id
     * @param clss
     * @return
     */
//    public boolean isExitObject(long id,Class clss);
    /*{
        QueryBuilder<T> qb = daoSession.getDao(clss).queryBuilder();
        qb.where(UserDao.Properties.Id.eq(id));
        long length = qb.buildCount().count();
        return length>0 ? true : false;
    }*/

    /**
     * 根据主键ID来查询
     *
     * @param id
     * @param clss
     * @return
     */
    public T QueryById(long id, Class clss) {
        return (T) daoSession.getDao(clss).loadByRowId(id);
    }


    /**
     * 查询某条件下的对象
     *
     * @param clss
     * @param where
     * @param params
     * @return
     */
    public List<T> QueryObject(Class clss, String where, String... params) {
        Object object = null;
        List<T> objects = null;
        try {
            object = daoSession.getDao(clss);
            if (null == object) {
                return null;
            }
            objects = daoSession.getDao(clss).queryRaw(where, params);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return objects;
    }

    public List<T> QueryListByDesc(Class clss, Property orderDesc, int limit, String where, Object... params) {
        Object object = null;
        List<T> objects = null;
        try {
            object = daoSession.getDao(clss);
            if (null == object) {
                return null;
            }
            objects = daoSession.getDao(clss).queryBuilder().where(new WhereCondition.StringCondition(where, params)).orderDesc(orderDesc).limit(limit).list();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return objects;
    }

    /**
     * 查询所有对象
     *
     * @param clss
     * @return
     */
    public List<T> QueryAll(Class clss) {
        List<T> objects = null;
        try {
            objects = daoSession.getDao(clss).loadAll();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return objects;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        daoManager.closeDataBase();
    }

    public void CloseDic() {
        dicDaoManager.closeDataBase();
    }
}
