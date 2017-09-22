package com.hxyd.dyt.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

/**
 * Created by win7 on 2017/3/3.
 */

public class MWebView extends WebView{



    public MWebView(Context context) {
        super(context);
        init(context);
    }

    public MWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setUseWideViewPort(true);
        settings.setAllowContentAccess(false);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDefaultTextEncodingName("utf-8");
    }


}


