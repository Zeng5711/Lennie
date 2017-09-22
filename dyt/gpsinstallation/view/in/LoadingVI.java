package com.hxyd.dyt.gpsinstallation.view.in;

import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;

/**
 * Created by win7 on 2017/9/14.
 */

public interface LoadingVI extends BaseI {
    void uploadImg(int type);

    void deletImg( int type,String url);

    void setLoading(int type,Loading loading);

    void setImeiInfo(ImeiInfo imeiInfo);
}
