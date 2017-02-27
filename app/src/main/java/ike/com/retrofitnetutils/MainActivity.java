package ike.com.retrofitnetutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ike.com.retrofitnetutils.Exception.ApiException;
import ike.com.retrofitnetutils.retrofitUtils.Api.ApiCallBack;
import ike.com.retrofitnetutils.retrofitUtils.Api.ApiService;
import ike.com.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
private String Tag="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testGet();
    }
    public void testGet(){
        Map<String,String> map=new HashMap<>();
        map.put("ps",5+"");
        map.put("pv",0+"");
        map.put("time",0+"");
        map.put("pt","gt");
        map.put("op",601+"");
        Map<String,String> map1=new HashMap<>();
        map1.put("data", JSON.toJSONString(map));
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.post("http://qqb.sdblo.xyz:10002/index.supreme", map1, new ApiCallBack<String>() {
            @Override
            public void onSucess(String result) {
                Log.e(Tag,"result:"+result);
            }
            @Override
            public void onError(ApiException e) {
                Log.e(Tag,"出错了:"+e.getMessage()+"，code:"+e.getCode());
            }


        });
    }
}
