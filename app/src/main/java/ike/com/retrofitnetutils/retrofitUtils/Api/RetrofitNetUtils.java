package ike.com.retrofitnetutils.retrofitUtils.Api;

import android.app.Application;
import android.content.Context;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
作者：ike
时间：2017/2/27 17:09
功能描述：retrofit网络请求工具类
**/

public class RetrofitNetUtils {
    private static Context context;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static ApiService apiService;
    private static final int DEFALT_TIME=10;
    public static final String BASE_URL="https://qqb.sdblo.xyz:11443/";
    private RetrofitNetUtils(){

    }
    /**
     * 采用链式建造者模式
     */
    public static final class Builder{

        public Builder(Context mContext) {
            context = mContext.getApplicationContext();
            okHttpBuilder=new OkHttpClient.Builder();
            retrofitBuilder=new Retrofit.Builder();
        }
        public RetrofitNetUtils build(){
            OkHttpClient okHttpClient = okHttpBuilder.connectTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .readTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient).build();
             apiService = retrofit.create(ApiService.class);
            return new RetrofitNetUtils();
        }

    }

    /**
     * 通用post请求封装
     * @return Subscription 订阅者
     */
    public <T> Subscription post(String url, Map<String,String> param,ApiCallBack<T> callBack){
        return apiService.post(url,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }
   


}
