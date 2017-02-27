package ike.com.retrofitnetutils.Exception;

/**
 * Created by ike on 2017/2/27.
 * 网络异常
 */

public class NetWorkException extends Exception {
    public NetWorkException() {
        super();
    }

    public NetWorkException(Throwable cause) {
        super(cause);
    }

    public NetWorkException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetWorkException(String message) {
        super(message);
    }
}
