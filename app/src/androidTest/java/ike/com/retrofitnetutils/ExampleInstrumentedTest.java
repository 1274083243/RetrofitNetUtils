package ike.com.retrofitnetutils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ike.com.retrofitnetutils.retrofitUtils.Api.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private String Tag="ExampleInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("ike.com.retrofitnetutils", appContext.getPackageName());
    }
    @Test
    public void testGet(){
        OkHttpClient.Builder okhttpBuilder=new OkHttpClient.Builder();
        Retrofit.Builder retrofitBuilder=new Retrofit.Builder();
        OkHttpClient client = okhttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = retrofitBuilder.baseUrl("https://qqb.sdblo.xyz:11443/")
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Map<String,String> map=new HashMap<>();
        map.put("ps",5+"");
        map.put("pv",0+"");
        map.put("time",0+"");
        map.put("pt","gt");
        map.put("op",601+"");
        Map<String,String> map1=new HashMap<>();
        map1.put("data", JSON.toJSONString(map));
        Call<ResponseBody> call = apiService.get("http://qqb.sdblo.xyz:10002/index.supreme", map1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(Tag,"成功:"+response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Tag,"失败:"+t.getMessage());
            }
        });
    }
}
