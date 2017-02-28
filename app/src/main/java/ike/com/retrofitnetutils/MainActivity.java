package ike.com.retrofitnetutils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ike.com.retrofitnetutils.Exception.ApiException;
import ike.com.retrofitnetutils.download.DownLoadInfo;
import ike.com.retrofitnetutils.intercepter.UploadProgressCallBack;
import ike.com.retrofitnetutils.model.ProgressRequestBody;
import ike.com.retrofitnetutils.retrofitUtils.Api.ApiCallBack;
import ike.com.retrofitnetutils.retrofitUtils.Api.ApiService;
import ike.com.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String Tag = "MainActivity";
    private Button upLoad;
    private Button downLoad;
    private final int IMAGE = 1;
    private TextView tv_pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upLoad = (Button) findViewById(R.id.upLoad);
        tv_pg= (TextView) findViewById(R.id.tv_pg);
        downLoad= (Button) findViewById(R.id.downLoad);
        upLoad.setOnClickListener(this);
        testGet();
    }

    public void testGet() {
        Map<String, String> map = new HashMap<>();
        map.put("ps", 5 + "");
        map.put("pv", 0 + "");
        map.put("time", 0 + "");
        map.put("pt", "gt");
        map.put("op", 601 + "");
        Map<String, String> map1 = new HashMap<>();
        map1.put("data", JSON.toJSONString(map));
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.post("http://qqb.sdblo.xyz:10002/index.supreme", map1, new ApiCallBack<String>() {
            @Override
            public void onSucess(String result) {
                Log.e(Tag, "result:" + result);
            }

            @Override
            public void onError(ApiException e) {
                Log.e(Tag, "出错了:" + e.getMessage() + "，code:" + e.getCode());
            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upLoad:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.downLoad:
                DownLoadInfo downInfo=new DownLoadInfo();
                downLoad(downInfo);
                break;
        }
    }

    /**
     * 文件下载
     * @param downInfo
     */
    private void downLoad(DownLoadInfo downInfo) {
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.downLoadFile(downInfo);
    }

    private String imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            Log.e(Tag, "imagePath:" + ("file://" + imagePath));
            upFile(imagePath);
            c.close();
        }
    }

    private void upFile(String imagePath) {
        Log.e(Tag, "开始上传");
        String up_url = "http://i1.sdblo.xyz:8091/up/";
        File file = new File(imagePath);
        Map<String, String> param = new HashMap<>();
        param.put("uid", "uid");
        ProgressRequestBody progressBody=new ProgressRequestBody(RequestBody.create(MediaType.parse("multipart/form-data"), file));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file_", file.getName(), progressBody);
        RetrofitNetUtils retrofitNetUtils = new RetrofitNetUtils.Builder(this).build();
        retrofitNetUtils.upLoadFile(up_url, param, part, new UploadProgressCallBack<String>(progressBody) {
            @Override
            public void onSucess(String result) {
                Log.e(Tag,"成功："+result);
            }

            @Override
            public void onError(ApiException e) {
                Log.e(Tag,"错误1111："+e.getCode());
            }

            @Override
            public void onProgress(Long current, Long total) {
                tv_pg.setText((int) (current*1.0f/total*100)+"");
                Log.e(Tag,"上传中:current"+current+",total:"+total);
            }

        });
    }
}
