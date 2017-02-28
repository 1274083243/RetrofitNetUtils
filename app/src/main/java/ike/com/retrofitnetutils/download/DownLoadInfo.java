package ike.com.retrofitnetutils.download;

/**
作者：ike
时间：2017/2/28 15:03
功能描述：下载实体信息
**/

public class DownLoadInfo {
    public String downLoadPath;//下载路径
    public String savePath;//存储路径
    public long totalLength;//文件总长度
    public long readLength;//文件下载长度
    public DownLoadManager.DownLoadState downLoadState;//下载状态
    public DownLoadListener downLoadListener;
}
