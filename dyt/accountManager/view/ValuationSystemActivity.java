package com.hxyd.dyt.accountManager.view;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.BaseActivity;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.MWebView;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public class ValuationSystemActivity extends BaseActivity {

    @BindView(R.id.activity_valuation_system_webview)
    MWebView mWebView;
    @BindView(R.id.activity_valuation_system)
    RelativeLayout mRl;

    private ProgressBar progressbar;
    private boolean falg = false;
    private WebClient mWebClient;
    private WebChromeClient mWebChromeClient;


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        isShowTR(false);
        isShowIR(false);
        isShowSpoy(false);

        setIL(R.mipmap.back);
        setTitle("贷业通车辆评估系统");
        getMTitle().setTextSize(16);

        falg = getIntent().getBooleanExtra("POST_DEALER_BUY_PRICE",false);
        progressbar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 8, 0, 0));
        mRl.addView(progressbar);
        mWebChromeClient = new WebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebClient = new WebClient();
        mWebView.setWebViewClient(mWebClient);

        mWebView.loadUrl(Constant.VALUATION_SYSTEM_URL);
    }

    @Override
    protected void addContentView() {
        setContentView(R.layout.activity_valuation_system);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mWebClient = null;
        mWebChromeClient = null;
//        progressbar = null;
        mWebView = null;
        Logger.e(" VA+++++++onDestroy++++++++++");
        super.onDestroy();

    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);    // 加载完成隐藏进度条
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            setTitle(title);
        }
    }

    public class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            Logger.e("url = >" + url);
            if(url.contains("https://www.che300.com/partner/result.php") && falg){
                view.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        mWebView.setVisibility(View.GONE);
                        ValuationSystemActivity.this.showProgressDialog("正在努力加载中....");
                    }
                });

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.che300.com/")
                        .build();
                Call<ResponseBody> call =  retrofit.create(webViewAPI.class).downHtml(URLRequest(url));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                StringBuilder builder = new StringBuilder(response.body().string().toString());
                                int  beginIndex = builder.indexOf("\"dealer_low_buy_price\":")+23;
                                int endIndex = builder.lastIndexOf(",\"dealer_low_auction_price\"");
//                                Logger.e("builder = >" + builder.toString());
                                String value = builder.substring(beginIndex,endIndex);
                                Logger.e("builder ========== >" + value);
                                EventBus.getDefault().post(new BusEvent(BusEvent.POST_DEALER_BUY_PRICE,value));

                            } catch (IOException e) {
                                view.getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Tools.makeText("获取评估值失败");
                                        ValuationSystemActivity.this.dismiss();
                                        ValuationSystemActivity.this.finish();
                                    }
                                });
                                e.printStackTrace();

                            }
                        }else{
                            view.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Tools.makeText("获取评估值失败");
                                }
                            });
                        }
                        view.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ValuationSystemActivity.this.dismiss();
                                ValuationSystemActivity.this.finish();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        view.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                Tools.makeText("获取评估值失败");
                                ValuationSystemActivity.this.dismiss();
                                ValuationSystemActivity.this.finish();
                            }
                        });

                    }
                });

            }

            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

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




    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, Object> URLRequest(String URL) {
        Map<String, Object> mapRequest = new HashMap<String, Object>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    public interface  webViewAPI{
        @Headers({
                "Accept: application/vnd.yourapi.v1.full+json",
                "Host: www.che300.com",
                "Content-Type: application/json; charset=utf-8",
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36",
                "Accept-Language: zh-CN,zh;q=0.8,en;q=0.6"
        })
        @POST("partner/result.php")
        Call<ResponseBody> downHtml(@QueryMap Map<String,Object> body);
    }

    private static boolean  save(byte[] bytes) {
        FileOutputStream fs = null;
        boolean b= false;
        try {

            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Lennie");
            if (!file.exists()) {
                Logger.e("mkdirs = ");
                file.mkdirs();
            }
            File file2 = new File(Environment.getExternalStorageDirectory() + File.separator + "Lennie" + File.separator + System.currentTimeMillis() + "test.txt");

            fs = new FileOutputStream(file2, false);
            fs.write(bytes);
            fs.flush();
            Logger.e("path = " + file.getAbsolutePath());
            b= true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

        return b;
    }


}
