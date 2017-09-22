// IMyAidlInterface.aidl
package lennie.org.lennie.aidl;

// Declare any non-default types here with import statements
//这里需要引入回调接口，不然编译器找不到IMyCallback这个接口
import lennie.org.lennie.aidl.IMyCallback;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void registerCallback(IMyCallback callback);
    void unregisterCallback(IMyCallback callback);
}
