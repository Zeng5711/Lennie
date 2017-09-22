package lennie.org.lennie.webview.interactive;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by win7 on 2017/7/14.
 */

public class HostJsScope {
    public static HostJsScope hostJsScope = new HostJsScope();
    static final String TAG = "HostJsScope";


    public static void toast (WebView webView, String message) {
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
        execJsMethod(webView,"toast","success");
    }

    /** webview历史返回 */
    public static boolean goBackOrForward(final WebView webView,
                                          String successCallback, String errorCallback, int index) {
        if (index != 0) {
            webView.goBackOrForward(index);
            execJsMethod(webView, successCallback, "{\"result\":\"" + "true" + "\"}");
            return true;
        }
        // index值不能为0
        execJsMethod(webView, errorCallback, "{\"error\":\"" + "index=0" + "\"}");
        return false;
    }

    private static void execJsMethod(WebView wv, String methodName,
                                     String params) {
        String jsContent = "javascript:" + methodName + "(\'" + params + "\')";
        Log.i(TAG, jsContent);
        wv.loadUrl(jsContent);
    }

}
