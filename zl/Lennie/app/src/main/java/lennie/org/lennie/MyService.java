package lennie.org.lennie;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import lennie.org.lennie.aidl.IMyAidlInterface;
import lennie.org.lennie.aidl.IMyCallback;

/**
 * Created by win7 on 2017/7/26.
 */

public class MyService extends Service {

    private static final String TAG = "MyService";
    private RemoteCallbackList<IMyCallback> callbackList = new RemoteCallbackList<IMyCallback>();

    //声明aidl接口
    private IMyAidlInterface.Stub mIBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.i(TAG, "anInt = " + anInt);
            Log.v(TAG, "aLong = " + aLong);
            Log.d(TAG, "aBoolean = " + aBoolean);
            Log.e(TAG, "aFloat = " + aFloat);
            Log.w(TAG, "aDouble = " + aDouble);
            onSuccessCallback();
            onErrorCallback();
        }

        @Override
        public void registerCallback(IMyCallback callback) throws RemoteException {
            if (callback != null) {
                Log.i(TAG,"registerCallback===>");
                callbackList.register(callback);
            }
        }

        @Override
        public void unregisterCallback(IMyCallback callback) throws RemoteException {
            if (callback != null) {
                callbackList.unregister(callback);
            }
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        //返回定义好的IBinder
        return mIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate == >");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand == >");
        return super.onStartCommand(intent, flags, startId);

    }

    private void onSuccessCallback() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                int beginBroadcast = callbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        callbackList.getBroadcastItem(i).onSuccess(1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                callbackList.finishBroadcast();
//            }
//        },1000);

    }

    private void onErrorCallback() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                int beginBroadcast = callbackList.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        callbackList.getBroadcastItem(i).onError("2");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                callbackList.finishBroadcast();
//            }
//        },1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callbackList.kill();
    }
}
