package ike.com.retrofitnetutils.download;

import ike.com.retrofitnetutils.Exception.ApiException;

/**
 * Created by ike  on 2017/2/28.
 * 下载状态监听
 */

public abstract class DownLoadListener<T> {
    public abstract void onStart();//下载开始
    public abstract void onComplete();//下载完成
    public abstract void onPause();//下载暂停
    public abstract void onNext(T t);//下载成功
    public abstract void onDownLoad(long currentLength,long countLength);//下载进度
    public abstract void onError(Throwable e);//下载出错
}
