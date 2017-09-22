package com.hxyd.dyt.accountManager.modle.in;

import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.Task;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/5/24.
 */

public interface TaskMI {
    Observable<Task> getAssessmentTask(Map<String, Object> map);

    Observable<GPSInstall> queryTaskListData(Map<String, Object> map);
}
