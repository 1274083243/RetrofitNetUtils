package ike.com.retrofitnetutils.retrofitUtils.Api;

import ike.com.retrofitnetutils.Exception.ApiException;

/**
 * Created by ike on 2017/2/27.
 * 请求结果回调函数
 */

public abstract class ApiCallBack<T> {
    public abstract void onSucess(T result);
    public abstract void onError(ApiException e);


}
