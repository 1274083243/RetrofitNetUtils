package ike.com.retrofitnetutils.retrofitUtils.Api;

import android.content.Context;
import android.util.Log;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

import ike.com.retrofitnetutils.Exception.ApiCode;
import ike.com.retrofitnetutils.Exception.ApiException;
import ike.com.retrofitnetutils.Exception.NetWorkException;
import ike.com.retrofitnetutils.commonUtils.NetworkUtils;
import rx.Subscriber;

/**
 * 所有订阅者的基类，先一步进行网络判断与错误处理
 */

public abstract class BaseSubScriber<T> extends Subscriber<T> {
    private String Tag="BaseSubScriber";
    private WeakReference<Context> mContext;

    public BaseSubScriber(Context context) {
        mContext=new WeakReference<Context>(context);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(mContext.get())){
            onError(new ApiException(new NetWorkException(),ApiCode.Request.NETWORK_ERROR));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException){
            onError(e);
        }else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }
    public abstract void onError(ApiException exception);
}
