package lennie.org.lennie.loading;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class ViewAcceleration {
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void viewAcceleration(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && null != view) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void hardwareAccelerated(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && null != view) {
//            if(view.isHardwareAccelerated()){
//                view.setLayerType(View.LAYER_TYPE_NONE, null);
//            }
        }
        
    }
}
