package lennie.org.lennie.webview.interactive;


import android.util.Log;
import android.webkit.WebView;

import java.lang.ref.WeakReference;

/**
 * Created by win7 on 2017/7/14.
 */

public class JsCallback {

    private static final String CALLBACK_JS_FORMAT = "javascript:HostApp.callback(%d, %d %s);";
    private int index;
    private WebView webView;
    private int isPermanent;
    private boolean mCouldGoOn;
    private WeakReference<WebView> mWebViewRef;
    public JsCallback (WebView view, int index) {
        mCouldGoOn = true;
        this.index = index;
        this.webView = view;
        mWebViewRef = new WeakReference<WebView>(view);
    }

    public void apply (Object... args) throws JsCallbackException {

        if (mWebViewRef.get() == null) {
            throw new JsCallbackException("the WebView related to the JsCallback has been recycled");
        }

        if (!mCouldGoOn) {
            throw new JsCallbackException("the JsCallback isn't permanent,cannot be called more than once");
        }

        StringBuilder sb = new StringBuilder();
        for (Object arg : args){
            sb.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                sb.append("\"");
            }
            sb.append(String.valueOf(arg));
            if (isStrArg) {
                sb.append("\"");
            }
        }
        String execJs = String.format(CALLBACK_JS_FORMAT, index, isPermanent, sb.toString());
        Log.i("JsCallBack", execJs);
        webView.loadUrl(execJs);
        mWebViewRef.get().loadUrl(execJs);
        mCouldGoOn = isPermanent > 0;
    }

    public void setPermanent (boolean value) {
        isPermanent = value ? 1 : 0;
    }

    public static class JsCallbackException extends Exception {
        public JsCallbackException (String msg) {
            super(msg);
        }
    }
}
