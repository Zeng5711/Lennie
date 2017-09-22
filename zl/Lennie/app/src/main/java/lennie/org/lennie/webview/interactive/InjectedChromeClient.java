package lennie.org.lennie.webview.interactive;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import lennie.org.lennie.webview.TestWebView;

/**
 * Created by win7 on 2017/7/14.
 */

    public class InjectedChromeClient extends WebChromeClient {

    private final String TAG = "InjectedChromeClient";
    private JsCallJava mJsCallJava;
    private boolean mIsInjectedJSA;

    private boolean mIsInjectedJSB;

    public InjectedChromeClient (String injectedName, Class injectedCls) {
        mJsCallJava = new JsCallJava(injectedName, injectedCls);
    }

    public InjectedChromeClient (Class injectedCls) {
        mJsCallJava = new JsCallJava(injectedCls);//js可调用的Java方法所在类
    }

    // 处理Alert事件
    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        result.confirm();
        return true;
    }

    @Override
    public void onProgressChanged (WebView view, int newProgress) {
        //为什么要在这里注入JS
        //1 OnPageStarted中注入有可能全局注入不成功，导致页面脚本上所有接口任何时候都不可用
        //2 OnPageFinished中注入，虽然最后都会全局注入成功，但是完成时间有可能太晚，当页面在初始化调用接口函数时会等待时间过长
        //3 在进度变化时注入，刚好可以在上面两个问题中得到一个折中处理
        //为什么是进度大于25%才进行注入，因为从测试看来只有进度大于这个数字页面才真正得到框架刷新加载，保证100%注入成功
        TestWebView mw = ((TestWebView)view);
        if (newProgress <= 25) {
            mIsInjectedJSA = false;
            mIsInjectedJSB = false;
        } else if (!mIsInjectedJSA) {
            Log.i("InjectedChromeClient", "--------------------》onProgressChanged：" + mJsCallJava.getPreloadInterfaceJS());
            mw.loadUrl(mJsCallJava.getPreloadInterfaceJS());
            mIsInjectedJSA = true;
            Log.i(TAG, " inject js interface completely on progress " + newProgress);
            mIsInjectedJSB = false;
        }else if(newProgress >= 90){
            if(!mIsInjectedJSB){
                Log.i("InjectedChromeClient", "--------------------》onProgressChanged：" + mJsCallJava.getPreloadInterfaceJS());
                mw.loadUrl(mJsCallJava.getPreloadInterfaceJS());
                mIsInjectedJSB = true;
            }
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        //message中传递的是 要调用的java代码的 方法名, 参数类型和具体参数
        result.confirm(mJsCallJava.call(view, message));//我们是这个是通过Prompt注册调用的js对象的
        //这里我们直接自己处理掉,不交给系统,交给系统会弹出输入信息的对话框的
        return true;
    }
}
