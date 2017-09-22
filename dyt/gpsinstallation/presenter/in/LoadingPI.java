package com.hxyd.dyt.gpsinstallation.presenter.in;



/**
 * Created by win7 on 2017/9/14.
 */

public interface LoadingPI {

    int positionImages =1;//安装位置照片
    int materialsImages =2;//抵押材料
    int contractImages =3;//合同资料
    int carImages =4;//车辆图片

    void uploadImg(int type,String businessId, String name, String pathURL,String imeiId);

    void queryAllImgByParams(int type,String businessId,String imeiId);

    void deleteImg(int type,String businessId, String name,String imeiId);

    void updateImeiInfo(String position,String imeiIds,String businessId);

    void updateLoanAudiByBussinessId(String businessId,String mortgageState);

    void saveCarEvaluation(String businessId,String isDormantLock,String dormantLockDesc ,
                           String isAccidentApplyCar,String surfaceDesc,String assessmentPrice,
                           String applyCarEvaluationDesc,String applyCarEvaluationResult,
                           String taskId,String processInstanceId);
}
