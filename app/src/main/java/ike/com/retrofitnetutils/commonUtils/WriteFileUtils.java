package ike.com.retrofitnetutils.commonUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import ike.com.retrofitnetutils.download.DownLoadInfo;
import okhttp3.ResponseBody;

/**
作者：ike
时间：2017/2/28 17:26
功能描述：文件写入工具类
**/

public class WriteFileUtils {
    /**
     * 写入文件
     * @param file
     * @param info
     * @throws IOException
     */
    public  static  void writeCache(ResponseBody responseBody, File file, DownLoadInfo info) throws IOException{
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.totalLength==0){
            allLength=responseBody.contentLength();
        }else{
            allLength=info.totalLength;
        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.readLength,allLength-info.readLength);
        byte[] buffer = new byte[1024*8];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
