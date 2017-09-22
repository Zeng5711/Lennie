package lennie.org.lennie;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BActivity extends Activity {


    private webView mWebView;

    String TAG = "B-Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate ===>");
        setContentView(R.layout.activity_b);

        mWebView = (webView)findViewById(R.id.webview);
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new WebClient());

//        mWebView.loadUrl("http://192.168.5.171:8080/xw/exchange/register");//192.168.5.171:8080/xw/exchange/register
    }


    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

    }

    public class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            Log.i("MyWebView","shouldOverrideUrlLoading   =  url ====>" + url);

//            if (url != null && url.startsWith("https://xwbk.lanmaoly.com/bha-neo-app/gateway/mobile/callback")) {
//
//                view.evaluateJavascript();
//
//            }


            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.i("MyWebView","onLoadResource   =  url ====>" + url);
            super.onLoadResource(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.i("MyWebView","shouldInterceptRequest   =  url ====>" + url);
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart ===>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume ===>");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause ===>");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop ===>");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart ===>");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy ===>");
    }
}
