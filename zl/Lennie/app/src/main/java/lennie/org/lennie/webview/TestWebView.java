package lennie.org.lennie.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by win7 on 2017/7/14.
 */

public class TestWebView extends WebView {
    public TestWebView(Context context) {
        this(context,null);
    }

    public TestWebView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TestWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**
     abstract void setAllowContentAccess(boolean allow)
     启用或禁用WebView中的内容URL访问。

     abstract void	setAllowFileAccess(boolean allow)
     启用或禁用WebView中的文件访问。

     abstract void	setAllowFileAccessFromFileURLs(boolean flag)
     设置是否允许在文件方案URL的上下文中运行的JavaScript是否可以从其他文件方案URL访问内容。

     abstract void	setAllowUniversalAccessFromFileURLs(boolean flag)
     设置是否允许在文件方案URL的上下文中运行的JavaScript是否可以从任何来源访问内容。

     abstract void	setAppCacheEnabled(boolean flag)
     设置应用程序缓存API是否应启用。

     abstract void	setAppCacheMaxSize(long appCacheMaxSize)
     此方法在API级别18中已被弃用。在将来的配额将被自动管理。

     abstract void	setAppCachePath(String appCachePath)
     设置应用程序缓存文件的路径。

     abstract void	setBlockNetworkImage(boolean flag)
     设置WebView是否不应从网络加载映像资源（通过http和https URI方案访问的资源）。

     abstract void	setBlockNetworkLoads(boolean flag)
     设置WebView是否不应该从网络加载资源。

     abstract void	setBuiltInZoomControls(boolean enabled)
     设置WebView是否应该使用其内置的缩放机制。

     abstract void	setCacheMode(int mode)
     覆盖缓存的使用方式。

     abstract void	setCursiveFontFamily(String font)
     设置草书字体系列名称。

     abstract void	setDatabaseEnabled(boolean flag)
     设置是否启用数据库存储API。

     abstract void	setDatabasePath(String databasePath)
     此方法在API级别19中已被弃用。数据库路径由实现进行管理，调用此方法将不起作用。

     abstract void	setDefaultFixedFontSize(int size)
     设置默认的固定字体大小。

     abstract void	setDefaultFontSize(int size)
     设置默认字体大小。

     abstract void	setDefaultTextEncodingName(String encoding)
     设置在解码html页面时使用的默认文本编码名称。

     abstract void	setDefaultZoom(WebSettings.ZoomDensity zoom)
     此方法在API级别19中已弃用。此方法不再受支持，有关推荐的替代方法，请参阅功能文档。

     abstract void	setDisabledActionModeMenuItems(int menuItems)
     根据menuItems标志禁用动作模式菜单项。

     abstract void	setDisplayZoomControls(boolean enabled)
     设置使用内置缩放机制时WebView是否应显示屏幕缩放控件。

     abstract void	setDomStorageEnabled(boolean flag)
     设置是否启用DOM存储API。

     abstract void	setEnableSmoothTransition(boolean enable)
     这种方法在API级别17中已经被弃用了。这种方法现在已经过时了，将来会成为一个无效的方法。

     abstract void	setFantasyFontFamily(String font)
     设置幻想字体系列名称。

     abstract void	setFixedFontFamily(String font)
     设置固定字体系列名称。

     abstract void	setGeolocationDatabasePath(String databasePath)
     这个方法在API级别24中已被弃用。地理位置数据库由实现进行管理，调用该方法将无效。

     abstract void	setGeolocationEnabled(boolean flag)
     设置是否启用地理位置。

     abstract void	setJavaScriptCanOpenWindowsAutomatically(boolean flag)
     告诉JavaScript自动打开窗口。

     abstract void	setJavaScriptEnabled(boolean flag)
     告诉WebView启用JavaScript执行。

     abstract void	setLayoutAlgorithm(WebSettings.LayoutAlgorithm l)
     设置底层布局算法。

     abstract void	setLightTouchEnabled(boolean enabled)
     此方法在API级别18中已被弃用。从此JELLY_BEAN设置已过时，无效。

     abstract void	setLoadWithOverviewMode(boolean overview)
     设置WebView是否以概览模式加载页面，也就是放大内容以适应屏幕宽度。

     abstract void	setLoadsImagesAutomatically(boolean flag)
     设置WebView是否加载图像资源。

     abstract void	setMediaPlaybackRequiresUserGesture(boolean require)
     设置WebView是否需要用户手势来播放媒体。

     abstract void	setMinimumFontSize(int size)
     设置最小字体大小。

     abstract void	setMinimumLogicalFontSize(int size)
     设置最小逻辑字体大小。

     abstract void	setMixedContentMode(int mode)
     当安全源代码尝试从不安全的来源加载资源时，配置WebView的行为。

     abstract void	setNeedInitialFocus(boolean flag)
     告诉WebView是否需要在requestFocus(int, android.graphics.Rect)调用节点时设置焦点 。

     abstract void	setOffscreenPreRaster(boolean enabled)
     设置此WebView在屏幕之外是否应该是栅格图块，但是附加到窗口。

     abstract void	setPluginState(WebSettings.PluginState state)
     此方法在API级别18中已弃用。将来不支持插件，不应使用。

     abstract void	setRenderPriority(WebSettings.RenderPriority priority)
     此方法在API级别18中已被弃用。不建议调整线程优先级，这在以后的版本中将不被支持。

     abstract void	setSafeBrowsingEnabled(boolean enabled)
     设置是否启用安全浏览。

     abstract void	setSansSerifFontFamily(String font)
     设置无衬线字体系列名称。

     abstract void	setSaveFormData(boolean save)
     设置WebView是否应保存表单数据。

     abstract void	setSavePassword(boolean save)
     此方法在API级别18中已被弃用。将来的版本中将不支持在WebView中保存密码。

     abstract void	setSerifFontFamily(String font)
     设置serif字体系列名称。

     abstract void	setStandardFontFamily(String font)
     设置标准字体系列名称。

     abstract void	setSupportMultipleWindows(boolean support)
     设置WebView是否支持多个窗口。

     abstract void	setSupportZoom(boolean support)
     设置WebView是否支持使用其屏幕缩放控件和手势进行缩放。

     void	setTextSize(WebSettings.TextSize t)
     这个方法在API级别14中被废弃了setTextZoom(int)。

     abstract void	setTextZoom(int textZoom)
     以百分比设置页面的文本缩放。

     abstract void	setUseWideViewPort(boolean use)
     设置WebView是否启用对“视口”HTML元标记的支持，或者应使用宽视口。

     abstract void	setUserAgentString(String ua)
     设置WebView的用户代理字符串。

     abstract boolean	supportMultipleWindows()
     获取WebView是否支持多个窗口。

     abstract boolean	supportZoom()
     获取WebView是否支持缩放。
     */
    private void init() {


        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);//是否支持使用其屏幕缩放控件和手势进行缩放
        settings.setBuiltInZoomControls(true);//设置WebView是否应该使用其内置的缩放机制。
        settings.setUseWideViewPort(true);//设置WebView是否启用对“视口”HTML元标记的支持，或者应使用宽视口。当设置的值为false时，布局宽度始终设置为与设备无关（CSS）像素中的WebView控件的宽度。当值为true并且页面包含视口元标记时，将使用标记中指定的宽度值。如果页面不包含标签或不提供宽度，则将使用宽视口。
        settings.setAllowContentAccess(false);//启用或禁用WebView中的内容URL访问。
        settings.setAppCacheEnabled(false);//设置应用程序缓存API是否应启用。默认值为false。请注意，为了启用Application Caches API，还必须提供有效的数据库路径
        settings.setAllowFileAccess(false);//启用或禁用WebView中的文件访问。
        settings.setDomStorageEnabled(false);//设置是否启用DOM存储API。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDefaultTextEncodingName("utf-8");//设置在解码html页面时使用的默认文本编码名称。
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.removeJavascriptInterface("accessibility");
            this.removeJavascriptInterface("accessibilityTraversal");
            this.removeJavascriptInterface("searchBoxJavaBridge_");
        }

    }


}
