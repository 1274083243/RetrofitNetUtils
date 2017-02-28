package ike.com.retrofitnetutils.download;

/**
作者：ike
时间：2017/2/28 16:51
功能描述：下载进度监听
**/
public interface DownLoadProgressListener {
    void upDownProgress(long currnetLength,long countLength,boolean isFinish);
}
