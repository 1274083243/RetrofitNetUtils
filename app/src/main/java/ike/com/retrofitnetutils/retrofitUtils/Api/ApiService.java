package ike.com.retrofitnetutils.retrofitUtils.Api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
作者：ike
时间： 16:09
功能描述：所有Api接口汇总
**/

public interface ApiService {
    /**
     * 普通的post请求
     * @param url：请求路径
     * @param params：请求参数
     * @return:返回请求数据结果
     */
    @FormUrlEncoded
    @POST()
    public Observable<String> post(@Url() String url, @FieldMap Map<String,String> params);
}
