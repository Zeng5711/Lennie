package lennie.org.lennie.loading;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/8/10.
 */

public class BusEvent {


    public static  final  int EVENT_WEBVIEW_RETRY = 1;


    public static  final  int EVENT_WEBVIEW_NAVI_IS_SHOW = 2;
    public static  final  int EVENT_SCALE_ALPHA = 3;
    public static  final  int LOADING_SATRT_ANIA = 4;
    public static  final  int LOADING_SHOW_CLOSEBUTTON = 5;
//    public static  final  int EVENT_WEBVIEW_RETRY = 1;
//    public static  final  int EVENT_WEBVIEW_RETRY = 1;
//    public static  final  int EVENT_WEBVIEW_RETRY = 1;
//    public static  final  int EVENT_WEBVIEW_RETRY = 1;
//    public static  final  int EVENT_WEBVIEW_RETRY = 1;


    public int type;

    public int paraInt;

    public String para;

    public Object object;


    public BusEvent(int e) {
        this.type = e;
    }

    public BusEvent(int e, String p) {
        this.type = e;
        this.para = p;
    }



    public BusEvent(int e, int p) {
        this.type = e;
        this.paraInt = p;
    }

    public BusEvent(int e, int p, Object o) {
        this.type = e;
        this.paraInt = p;
        this.object = o;
    }

    public BusEvent(int e, String p, Object o) {
        this.type = e;
        this.para = p;
        this.object = o;
    }


}
