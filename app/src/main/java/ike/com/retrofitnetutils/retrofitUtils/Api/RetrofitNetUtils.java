package ike.com.retrofitnetutils.retrofitUtils.Api;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ike.com.retrofitnetutils.commonUtils.WriteFileUtils;
import ike.com.retrofitnetutils.download.DownLoadInfo;
import ike.com.retrofitnetutils.download.DownLoadProgressListener;
import ike.com.retrofitnetutils.download.DownLoadSubScriber;
import ike.com.retrofitnetutils.intercepter.CommonParamIntercepter;
import ike.com.retrofitnetutils.intercepter.DownLoadIntercepter;
import ike.com.retrofitnetutils.intercepter.UploadProgressCallBack;
import ike.com.retrofitnetutils.model.ProgressRequestBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
作者：ike
时间：2017/2/27 17:09
功能描述：retrofit网络请求工具类
**/

public class RetrofitNetUtils {
    private String Tag="RetrofitNetUtils";
    private static Context context;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static ApiService apiService;
    private static final int DEFALT_TIME=10;
    private DownLoadProgressListener downLoadProgressListener;
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

        /**
         * 添加下载拦截器
         * @param listener
         * @return
         */
        public Builder downLoadIntercepter(DownLoadProgressListener listener){
            okHttpBuilder.addInterceptor(new DownLoadIntercepter(listener));
            return this;
        }
        public RetrofitNetUtils build(){
            OkHttpClient okHttpClient = okHttpBuilder.connectTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .readTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .writeTimeout(DEFALT_TIME, TimeUnit.SECONDS)
                    .addInterceptor(new CommonParamIntercepter())
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

    /**
     * 通用文件上传
     * @param url
     * @param param
     * @param file
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscription upLoadFile(String url, Map<String,String> param, MultipartBody.Part file, UploadProgressCallBack<T> callBack){
        return apiService.upLoadFile(url,param,file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallBackSubscriber(context, callBack));
    }

    /**
     * 文件断点下载
     * @param info：文件下载包装类
     * @param <T>
     * @return
     */
    public <T> Subscription downLoadFile(final DownLoadInfo info,DownLoadSubScriber downLoadSubScriber){
        Log.e(Tag,"开始下载");
        return apiService.downLoadFile(info.downLoadPath,"bytes="+info.readLength+"-")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody,DownLoadInfo>() {
                    @Override
                    public DownLoadInfo call(ResponseBody responseBody) {
                        try {
                            WriteFileUtils.writeCache(responseBody,new File(info.savePath),info);
                        } catch (IOException e) {
                            /*失败抛出异常1233444*/
                        }
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downLoadSubScriber);
    }

}
