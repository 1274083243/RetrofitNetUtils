package ike.com.retrofitnetutils.download;

import java.lang.ref.WeakReference;

import ike.com.retrofitnetutils.BaseApplication;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
作者：ike
时间：2017/2/28 15:08
功能描述：下载信息订阅者
**/

public class DownLoadSubScriber<T> extends Subscriber<T> implements DownLoadProgressListener{
    private WeakReference<DownLoadListener> downLoadListener;
    private DownLoadInfo downLoadInfo;
    public DownLoadSubScriber(DownLoadInfo downLoadInfo) {
        this.downLoadListener =new WeakReference<DownLoadListener>(downLoadInfo.downLoadListener);
        this.downLoadInfo=downLoadInfo;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (downLoadListener.get()!=null){
            downLoadListener.get().onStart();
        }
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.START;
        BaseApplication.info=downLoadInfo;
    }

    @Override
    public void onCompleted() {
        if (downLoadListener.get()!=null){
            downLoadListener.get().onComplete();
        }
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.FINISH;
        BaseApplication.info=downLoadInfo;
    }

    @Override
    public void onError(Throwable e) {
        if (downLoadListener.get()!=null){
            downLoadListener.get().onError(e);
        }
        downLoadInfo.downLoadState= DownLoadManager.DownLoadState.ERROR;
        BaseApplication.info=downLoadInfo;
    }

    @Override
    public void onNext(T t) {
        if (downLoadListener.get()!=null){
            downLoadListener.get().onNext(t);
        }
    }

    @Override
    public void upDownProgress( long currnetLength, long countLength, boolean isFinish) {
        if(downLoadInfo.totalLength>countLength){
            currnetLength=downLoadInfo.totalLength-countLength+currnetLength;
        }else{
            downLoadInfo.totalLength=countLength;
        }

        downLoadInfo.readLength=currnetLength;
        if (downLoadListener.get()!=null){
            Observable.just(currnetLength).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (downLoadInfo.downLoadState!= DownLoadManager.DownLoadState.PAUSE){
                                downLoadInfo.downLoadState= DownLoadManager.DownLoadState.DOWN_LOADING;
                                downLoadListener.get().onDownLoad(aLong,downLoadInfo.totalLength);
                            }
                        }
                    });
        }
        BaseApplication.info=downLoadInfo;
    }
}
