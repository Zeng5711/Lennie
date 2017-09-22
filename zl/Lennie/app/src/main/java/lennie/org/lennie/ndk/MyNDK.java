package lennie.org.lennie.ndk;

/**
 * Created by win7 on 2017/6/16.
 */

public class MyNDK {

    public native static String hello();

    //    默认是NDK.moduleName
    static {
        System.loadLibrary("ndk_test");//此处是生成.so文件
    }
}
