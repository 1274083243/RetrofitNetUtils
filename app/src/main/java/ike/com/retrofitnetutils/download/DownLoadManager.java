package ike.com.retrofitnetutils.download;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ike.com.retrofitnetutils.BaseApplication;
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
    private Map<String,DownLoadSubScriber> downLoadSubScriberMap=new HashMap<>();//下载观察者集合
    private List<DownLoadInfo> downLoadInfos=new ArrayList<>();//下载对象集合
    public enum DownLoadState{
        START,//开始
        PAUSE,//暂停
        ERROR,//下载出错
        DOWN_LOADING,//下载
        FINISH//xi下载完成
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
//        if (downLoadInfos.contains(downInfo)&&){
//            return;
//        }
        DownLoadSubScriber subScriber=new DownLoadSubScriber(downInfo);
        downLoadInfos.add(downInfo);
        downLoadSubScriberMap.put(downInfo.downLoadPath,subScriber);
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(mContext).downLoadIntercepter(subScriber).build();
        retrofitNetUtils.downLoadFile(downInfo,subScriber);
    }

    /**
     * 暂停下载
     * @param downInfo
     */
    public void pauseDownLoad(DownLoadInfo downInfo){
        if (downInfo==null){
            return;
        }
        downInfo.downLoadState=DownLoadState.PAUSE;
        downInfo.downLoadListener.onPause();
        DownLoadSubScriber scriber = downLoadSubScriberMap.get(downInfo.downLoadPath);
        if (scriber!=null){
            scriber.unsubscribe();
            downLoadSubScriberMap.remove(downInfo.downLoadPath);
        }
        BaseApplication.info=downInfo;
    }

}
