package com.hxyd.dyt.accountManager.presenter.in;

import java.util.Map;

/**
 * Created by win7 on 2017/5/24.
 */

public interface TaskPI {

    void getAssessmentTask(String pageIndex,String isComplete);

    void queryTaskListData(int queryType,int taskDefKey,int pageIndex);
}
