package ike.com.retrofitnetutils;

import android.app.Application;
import android.content.Context;

import ike.com.retrofitnetutils.download.DownLoadInfo;

/**
 * Created by dell on 2017/2/27.
 */

public class BaseApplication extends Application {
    public static Context context;
    public static DownLoadInfo info;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

    }
}
