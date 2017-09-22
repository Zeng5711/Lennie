package lennie.org.lennie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import lennie.org.lennie.loading.GameLoadingView;
import lennie.org.lennie.ndk.MyNDK;

public class MainActivity extends AppCompatActivity {

    String TAG = "A-Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate ===>");

        GameLoadingView loadingView = new GameLoadingView(this);


        setContentView(loadingView);
        loadingView.startLoading(GameLoadingView.RING_AND_GAME_MODE);
//        String message = MyNDK.hello();
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,AIDLActivity.class));
////                MainActivity.this.finish();
//            }
//        });

//        this.registerReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart ===>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume ===>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause ===>");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop ===>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart ===>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy ===>");

    }
}
