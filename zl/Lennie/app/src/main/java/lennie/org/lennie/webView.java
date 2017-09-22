package lennie.org.lennie;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lennie.org.lennie.webview.HostJsScope2;
import lennie.org.lennie.webview.TestWebView;
import lennie.org.lennie.webview.interactive.HostJsScope;
import lennie.org.lennie.webview.interactive.InjectedChromeClient;
import lennie.org.lennie.webview.interactive.JsCallJava;

/**
 * Created by win7 on 2017/7/5.
 */

public class webView  extends TestWebView {

    public webView(Context context) {
        super(context);
        init(context);
    }

    public webView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public webView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        this.setWebViewClient(webViewClient);
        this.setWebChromeClient(new InjectedChromeClient(HostJsScope2.class));

        loadUrl("file:///android_asset/test.html");
    }

    private WebViewClient webViewClient =  new WebViewClient(){



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

}
