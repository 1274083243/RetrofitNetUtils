package ike.com.retrofitnetutils.intercepter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
作者：ike
时间：2017/2/28 9:17
功能描述：通用参数拦截器
**/

public class CommonParamIntercepter implements Interceptor {
    private String Tag="CommonParamIntercepter";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        if (request.method().equals("POST")){
           if (request.body() instanceof FormBody){
               FormBody.Builder bodyBuilder=new FormBody.Builder();
               FormBody body= (FormBody) request.body();
               for (int i = 0; i < body.size(); i++) {
                   if (body.encodedName(i).equals("data")){
                       String encode = URLDecoder.decode(body.encodedValue(i),"UTF-8");
                       JSONObject jsonObject = JSON.parseObject(encode);
                       jsonObject.put("version","2.2.0");
                       String s = jsonObject.toString();
                       bodyBuilder.add(body.encodedName(i),s);
                   }
               }
               FormBody formBody = bodyBuilder.build();
               request= request.newBuilder().post(formBody).build();
           }
        }
        return chain.proceed(request);
    }
}
