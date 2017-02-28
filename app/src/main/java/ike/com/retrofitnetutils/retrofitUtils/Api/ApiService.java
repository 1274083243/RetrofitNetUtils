package ike.com.retrofitnetutils.retrofitUtils.Api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
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
    Observable<String> post(@Url() String url, @FieldMap Map<String,String> params);

    /**
     * 单文件带参数上传请求
     * @param url:请求路径
     * @param param：请求参数
     * @param file：上传文件
     */
    @Multipart
    @POST
    Observable<String> upLoadFile(@Url String url, @PartMap Map<String,String> param, @Part MultipartBody.Part file);

    /**
     * 文件断点续传下载
     * @param url：请求路径
     * @param start：文件开始下载的位置
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@Url String url, @Header("RANGE") String start);
}
