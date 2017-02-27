package ike.com.retrofitnetutils;

import android.app.Application;
import android.content.Context;

/**
 * Created by dell on 2017/2/27.
 */

public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
