package com.hxyd.dyt.accountManager.presenter;

import com.hxyd.dyt.accountManager.modle.TaskM;
import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.Task;
import com.hxyd.dyt.accountManager.modle.in.TaskMI;
import com.hxyd.dyt.accountManager.presenter.in.TaskPI;
import com.hxyd.dyt.accountManager.view.in.TaskVI;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.http.FilterSubscriber;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2017/5/24.
 */

public class TaskP implements TaskPI {

    private TaskMI M;

    private TaskVI V;

    public TaskP(TaskVI V) {
        this.V = V;
        M = new TaskM();
    }

    @Override
    public void getAssessmentTask(String pageIndex, String isComplete) {
        if (M != null && V != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());
            map.put("pageIndex", pageIndex);
            map.put("isComplete", isComplete);
            map = Constant.addMap(map);
            M.getAssessmentTask(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FilterSubscriber<Task>() {
                        @Override
                        public void onNext(Task data) {
                            if (V != null) {
                                V.onSetInfolist(data);
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(String str) {
                            if (V != null) {
                                V.onErr(1, str);
                            }

                        }
                    });

        }
    }

    @Override
    public void queryTaskListData(int queryType, int taskDefKey,int pageIndex) {

        V.showDialg("正在努力加载中...");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TOKEN_MAP_KEY, Constant.getToken());

        if (queryType == 0) {
            map.put("queryType", "agencyTaskTab");
        } else {
            map.put("queryType", "processedTaskTab");
        }

        if (taskDefKey == 3) {
            map.put("taskDefKey", "usertask_assess");
        } else if (taskDefKey == 4) {
            map.put("taskDefKey", "usertask_gps");
        } else if (taskDefKey == 5) {
            map.put("taskDefKey", "usertask_mortgage");
        }

        map.put("pageIndex", pageIndex);
        map = Constant.addMap(map);

        M.queryTaskListData(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<GPSInstall>() {
                    @Override
                    public void onNext(GPSInstall data) {
                        V.dissm();
                        V.setGPSInstall(data);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(String str) {
                        V.onErr(1, str);
                        V.dissm();
                    }
                });


    }
}
