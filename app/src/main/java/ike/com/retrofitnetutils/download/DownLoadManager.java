package ike.com.retrofitnetutils.download;

import android.content.Context;

import ike.com.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils;
import ike.com.retrofitnetutils.threadManager.ThreadManager;

/**
作者：ike
时间：2017/2/28 14:58
功能描述：下载管理类
**/

public class DownLoadManager {
    private static volatile  DownLoadManager INTANCE;
    private static Context mContext;
    enum DownLoadState{
        START,//开始
        PAUSE,//暂停
        ERROR,//下载出错
        DOWN_LOADING,//下载
        FINISH
    }
    private DownLoadManager(){

    }
    /**
     * 创建DownLoadManager单例
     * @return
     */
    public static DownLoadManager getInstance(Context context){
        mContext=context.getApplicationContext();
        if (INTANCE==null){
            synchronized (DownLoadManager.class){
                if (INTANCE==null){
                    INTANCE=new DownLoadManager();
                }
            }
        }
        return INTANCE;
    }

    /**
     * 开始下载
     */
    public void startDownLoad(DownLoadInfo downInfo){
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(mContext).build();
        retrofitNetUtils.downLoadFile(downInfo);
    }

}
