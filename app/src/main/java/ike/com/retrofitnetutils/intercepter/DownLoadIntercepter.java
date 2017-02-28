package ike.com.retrofitnetutils.intercepter;

import java.io.IOException;

import ike.com.retrofitnetutils.download.DownLoadProgressListener;
import ike.com.retrofitnetutils.download.DownloadResponseBody;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by ike on 2017/2/28.
 */

public class DownLoadIntercepter implements Interceptor{
    private DownLoadProgressListener downLoadProgressListener;

    public DownLoadIntercepter(DownLoadProgressListener downLoadProgressListener) {
        this.downLoadProgressListener = downLoadProgressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(new DownloadResponseBody(response.body(),downLoadProgressListener)).build();
    }
}
