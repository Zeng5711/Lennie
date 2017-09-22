package com.hxyd.dyt.accountManager.presenter.in;

/**
 * Created by win7 on 2017/3/14.
 */

public interface OrderPI {

    void getInfolist(String token, String pageIndex, String queryParameter, boolean isShow);

    void onDestroy();

}
