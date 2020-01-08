package com.quickdev.quickdevframework.db.dao;


import android.content.Context;

import com.quickdev.baseframework.utils.AppContextUtil;


public class DaoUtils {
    private static BaseDao baseDao;
    private static BaseDao dicBaseDao;

    /**
     * 获取逻辑数据库实例
     * @return
     */
    public static BaseDao getInstance(Context context){
        if(null == baseDao){
            baseDao = new BaseDao();
            baseDao.handleITron(context);
        }
        return baseDao;
    }

    /**
     * 获取逻辑数据库实例
     * @return
     */
    public static BaseDao getInstance(){
        if(null == baseDao){
            baseDao = new BaseDao();
            baseDao.handleITron(AppContextUtil.getContext());
        }
        return baseDao;
    }


    /**
     * 获取字典数据库实例
     * @return
     */
    public static BaseDao getDicInstance(){
        if(null == dicBaseDao){
            dicBaseDao = new BaseDao();
            dicBaseDao.handleDic();
        }
        return dicBaseDao;
    }

    /**
     * 关闭逻辑和字典数据库
     */
    public static void closeDataBase(){
        if(baseDao!=null) {
            baseDao.closeDataBase();
            baseDao = null;
        }
        if(dicBaseDao!=null) {
            dicBaseDao.CloseDic();
            dicBaseDao = null;
        }
    }
    /**
     * 关闭字典数据库
     */
    public static void closeDicBase(){
        dicBaseDao.CloseDic();
    }

}
