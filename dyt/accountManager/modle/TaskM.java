package com.hxyd.dyt.accountManager.modle;

import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.Task;
import com.hxyd.dyt.accountManager.modle.in.TaskMI;
import com.hxyd.dyt.common.http.GitHubAPI;
import com.hxyd.dyt.common.http.GithubServer;
import com.hxyd.dyt.common.http.ResponseFunc;

import java.util.Map;

import rx.Observable;

/**
 * Created by win7 on 2017/5/24.
 */

public class TaskM implements TaskMI {
    @Override
    public Observable<Task> getAssessmentTask(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().getAssessmentTask(map).map(new ResponseFunc<Task>());
    }

    @Override
    public Observable<GPSInstall> queryTaskListData(Map<String, Object> map) {
        return GitHubAPI.craetRetrofit().queryTaskListData(map).map(new ResponseFunc<GPSInstall>());
    }
}
