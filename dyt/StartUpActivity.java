package com.hxyd.dyt;

import android.os.Bundle;
import android.app.Activity;

public class StartUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
//        mUnbinder = ButterKnife.bind(this);
////        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        Intent intent = new Intent(MainActivity.this,Test.class);
////                        startActivity(intent);
////                        finish();
////                        overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
//
////                    }
////                },3000);
////            }
////        });
    }

}
