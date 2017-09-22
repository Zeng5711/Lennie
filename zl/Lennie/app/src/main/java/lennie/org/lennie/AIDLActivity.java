package lennie.org.lennie;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lennie.org.lennie.aidl.IMyAidlInterface;
import lennie.org.lennie.aidl.IMyCallback;

public class AIDLActivity extends Activity {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_)
    TextView tv_;

    private IMyAidlInterface myAidlInterface;

    //实现IMyCallback接口
    private IMyCallback myCallback = new IMyCallback.Stub() {

        @Override
        public void onSuccess(final int i) throws RemoteException {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(" i = " + i);
                }
            });
        }

        @Override
        public void onError(final String s) throws RemoteException {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    tv_.setText(" s = " + s);
                }
            });
        }
    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);

            try {
                myAidlInterface.registerCallback(myCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            Toast.makeText(AIDLActivity.this,"绑定成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            try {
                myAidlInterface.unregisterCallback(myCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            myAidlInterface = null;

            Toast.makeText(AIDLActivity.this,"解开绑定",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);

        Intent intent = new Intent(this,MyService.class);
        //在5.0及以上版本必须要加上这个
        intent.setPackage("service.hht.com.serviceapplication");
//        intent.setAction("lennie.org.lennie");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @OnClick(R.id.bt)
    public void onClick(View v) {

        if(myAidlInterface!=null){
            try {
                myAidlInterface.basicTypes(1,1,true, 1,2.2,"123");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(AIDLActivity.this,"服务未启动",Toast.LENGTH_SHORT).show();
        }
    }


}
