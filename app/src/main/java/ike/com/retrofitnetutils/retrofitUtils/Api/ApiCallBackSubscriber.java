package ike.com.retrofitnetutils.retrofitUtils.Api;

import android.content.Context;

import ike.com.retrofitnetutils.Exception.ApiException;

/**
 * Created by ike on 2017/2/27.
 * 接口回调订阅
 */

public class ApiCallBackSubscriber<T> extends BaseSubScriber<T> {
    protected  ApiCallBack<T> mCallBacck;
    public ApiCallBackSubscriber(Context context,ApiCallBack<T> callBack) {
        super(context);
        this.mCallBacck=callBack;
    }

    @Override
    public void onError(ApiException exception) {
        mCallBacck.onError(exception);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        mCallBacck.onSucess(t);
    }
}
