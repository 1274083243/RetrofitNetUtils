package ike.com.retrofitnetutils.retrofitUtils.Api;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
作者：ike
时间：2017/2/27 17:09
功能描述：retrofit网络请求工具类
**/

public class RetrofitNetUtils {
    public static  volatile RetrofitNetUtils intance;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;
    private static ApiService apiService;
    private static final int DEFALT_TIME=10;
    public static final String BASE_URL="https://qqb.sdblo.xyz:11443/";
    private RetrofitNetUtils(){

    }
    /**
     * RetrofitNetUtils类的单例实现
     * @return
     */
    public static RetrofitNetUtils getInstance(){
        if (intance==null){
            synchronized (RetrofitNetUtils.class){
                if (intance==null){
                    intance=new RetrofitNetUtils();
                }
            }
        }
        return intance;
    }

    /**
     * 采用链式建造者模式
     */
    public static final class Builder{
        private Context context;
        public Builder(Context context) {
            this.context = context.getApplicationContext();
            okHttpBuilder=new OkHttpClient.Builder();
            retrofitBuilder=new Retrofit.Builder();
        }
        public RetrofitNetUtils build(){
            OkHttpClient okHttpClient = okHttpBuilder.connectTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .readTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient).build();
             apiService = retrofit.create(ApiService.class);
            return new RetrofitNetUtils();
        }
    }


}
