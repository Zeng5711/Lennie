// IMyCallback.aidl
package lennie.org.lennie.aidl;

// Declare any non-default types here with import statements

interface IMyCallback {

    void onSuccess(int i);
    void onError(String s);

}
