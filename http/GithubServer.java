package com.hxyd.dyt.common.http;


import com.google.gson.JsonArray;
import com.hxyd.dyt.accountManager.modle.entity.CountTotal;
import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.ImageInfo;
import com.hxyd.dyt.accountManager.modle.entity.Information;
import com.hxyd.dyt.accountManager.modle.entity.JingZhengu;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfo;
import com.hxyd.dyt.accountManager.modle.entity.LoanInfoID;
import com.hxyd.dyt.accountManager.modle.entity.OrderDefultInfo;
import com.hxyd.dyt.accountManager.modle.entity.OrderInfo;
import com.hxyd.dyt.accountManager.modle.entity.ProductInfoList;
import com.hxyd.dyt.accountManager.modle.entity.Task;
import com.hxyd.dyt.accountManager.modle.entity.Vehicleinfo;
import com.hxyd.dyt.accountManager.modle.entity.Version;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.common.entity.AreaList;
import com.hxyd.dyt.common.entity.BaseData;
import com.hxyd.dyt.gpsinstallation.entity.CarInfo;
import com.hxyd.dyt.gpsinstallation.entity.DeviceInfo;
import com.hxyd.dyt.gpsinstallation.entity.ImeiInfo;
import com.hxyd.dyt.gpsinstallation.entity.Loading;
import com.hxyd.dyt.jpush.modle.entity.MessageList;
import com.hxyd.dyt.login.modle.entity.UserInfo;
import com.hxyd.dyt.purplestar.modle.entity.AlarmScreen;
import com.hxyd.dyt.purplestar.modle.entity.Container;
import com.hxyd.dyt.purplestar.modle.entity.DeviceLocation;
import com.hxyd.dyt.purplestar.modle.entity.GPS;
import com.hxyd.dyt.purplestar.modle.entity.PurpleStar;
import com.hxyd.dyt.purplestar.modle.entity.RecorShow;
import com.hxyd.dyt.vehiclemortgage.entity.VehicleEvaluation;


import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by win7 on 2017/2/16.
 */

public interface GithubServer {

    @POST("/app/version/check")
//@Query("verCode") String verCode, @Query("type") String type, @Query("posttime") String posttime,@Query("sign") String sign
    Observable<Result<Version>> check(@QueryMap Map<String, Object> map);

//    @POST("/app/main/base")
//    Observable<Result<Object>> base(@Query("posttime") String posttime,@Query("sign") String sign);

    @POST("/app/auth/main/message")
//@Query("token") String token, @Query("pageIndex") String pageIndex, @Query("posttime") String posttime,@Query("sign") String sign
    Observable<Result<Object>> message(@QueryMap Map<String, Object> map);

    @POST("/app/enc/login")
    Observable<Result<UserInfo>> login(@QueryMap Map<String, Object> map);

    @POST("/app/auth/enc/password/update")
    Observable<Result<Object>> update(@QueryMap Map<String, Object> map);

    @POST("/app/auth/mine/logout")
        //@Query("token") String token, @Query("posttime") String posttime,@Query("sign") String sign
    Observable<Result<Object>> logout(@QueryMap Map<String, Object> map);

//    @POST("/app/auth/loan/queryCustomerInfo")
//    Observable<Result<UserInfo>> queryCustomerInfo(@Query("loanInfoId") String loanInfoId, @Query("token") String token,@Query("posttime") String sign);

    @POST("/app/auth/loan/saveCustomerInfo")
    Observable<Result<LoanInfoID>> saveCustomerInfo(@QueryMap Map<String, Object> map);

//    @POST("/app/auth/loan/queryLoanInfo")
//    Observable<Result<LoanInfo>> queryLoanInfo(@Query("loanInfoId") String loanInfoId, @Query("token") String token,@Query("posttime") String sign);

    @POST("/app/auth/loan/saveLoanInfo")
    Observable<Result<Object>> saveLoanInfo(@QueryMap Map<String, Object> map);

//    @POST("/app/auth/loan/queryCarInfo")
//    Observable<Result<Vehicleinfo>> queryCarInfo(@Query("loanInfoId") String loanInfoId, @Query("token") String token,@Query("sign") String sign);

    @POST("/app/auth/loan/saveCarInfo")
    Observable<Result<Object>> saveCarInfo(@QueryMap Map<String, Object> map);

    @POST("/app/common/getSelectData")
//@Query("posttime") String posttime,@Query("sign") String sign
    Observable<Result<BaseData>> getSelectData(@QueryMap Map<String, Object> map);

    @POST("/app/common/getAreaList")
//@Query("posttime") String posttime,@Query("sign") String sign
    Observable<ResultT<AreaList>> getAreaList(@QueryMap Map<String, Object> map);

    @Multipart
    @POST()
//@Query("token") String token, @Query("loanId") String loanId, @Query("posttime") String posttime,@Query("sign") String sign
    Observable<Result<CountTotal>> imgUpload(@Url String url, @QueryMap Map<String, Object> map,
                                             @PartMap() Map<String, RequestBody> params);

//    @POST("/app/auth/image/imgReadAll")
//    Observable<Result<List<ImageInfo>>> imgReadAll(@Query("token") String token, @Query("loanId") String loanId, @Query("posttime") String posttime,@Query("sign") String sign);

    @POST("/app/auth/loan/infolist")
        //@Query("token") String token, @Query("pageIndex") String pageIndex, @Query("queryParameter") String queryParameter, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<OrderInfo>> infolist(@QueryMap Map<String, Object> map);

    @POST("/app/auth/loan/loandetail")
//@Query("token") String token, @Query("orderNo") String orderNo, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<OrderDefultInfo>> loandetail(@QueryMap Map<String, Object> map);

    @POST("/app/auth/messageList")
//@Query("token") String token, @Query("pageIndex") String pageIndex, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<MessageList>> messageList(@QueryMap Map<String, Object> map);

    @POST("/app/auth/common/getProductInfoList")
//@Query("token") String token, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<ProductInfoList>> getProductInfoList(@QueryMap Map<String, Object> map);

    @POST("/app/auth/main/mainInfo")
    Observable<Result<CountTotal>> mainInfo(@QueryMap Map<String, Object> map);

    @Streaming
    @GET
    Call<ResponseBody> downAPK(@Url String url);

    @POST("/app/auth/loan/syncAppData")
        //@Query("token") String token, @Query("loanId") String loanId, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<Object>> syncAppData(@QueryMap Map<String, Object> map);

    @POST("/app/auth/image/deleteFile")
        //@Query("token") String token, @Query("name") String name, @Query("loanId") String loanId, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<Object>> deleteFile(@QueryMap Map<String, Object> map);

    @POST("/app/auth/readImageSample")
        //@Query("token") String token, @Query("posttime") String posttime, @Query("sign") String sign
    Observable<Result<Information>> readImageSample(@QueryMap Map<String, Object> map);

    @POST("/app/auth/getJingZhenGuData")
    Observable<Result<JsonArray>> getJingZhenGuData(@QueryMap Map<String, Object> map);

    @POST("/app/auth/getJingZhenGuObjectData")
    Observable<Result<JingZhengu>> getJingZhenGuObjectData(@QueryMap Map<String, Object> map);

    @POST("/app/auth/loan/recordLoan")
    Observable<Result<LoanInfoID>> recordLoan(@QueryMap Map<String, Object> map);

    @POST("/app/auth/loan/customerInformationInput")
    Observable<Result<LoanInfoID>> customerInformationInput(@QueryMap Map<String, Object> map);

    @POST("/app/auth/loan/loanAndCarMessageInput")
    Observable<Result<LoanInfoID>> loanAndCarMessageInput(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/getAssessmentTask")
    Observable<Result<Task>> getAssessmentTask(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/saveAssessmentData")
    Observable<Result<Object>> saveAssessmentData(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/evaluateInformationDetails")
    Observable<Result<CarData>> evaluateInformationDetails(@QueryMap Map<String, Object> map);


    //============================GPS=======================================================================================

    @POST("/app/gps/getStoreList")
    Observable<Result<PurpleStar>> getStoreList(@QueryMap Map<String, Object> map);

    @POST("/app/gps/getEquipmentList")
    Observable<Result<Container>> getEquipmentList(@QueryMap Map<String, Object> map);


    @POST("/app/gps/getEquipmentListForSuperManager")
    Observable<Result<Container>> getEquipmentListForSuperManager(@QueryMap Map<String, Object> map);

    @POST("/app/gps/getAlarmList")
    Observable<Result<Container>> getAlarmList(@QueryMap Map<String, Object> map);

    @POST("/app/gps/getAlarmScreeningList")
    Observable<Result<AlarmScreen>> getAlarmScreeningList(@QueryMap Map<String, Object> map);

    @POST("/app/gps/getLocationInfo")
    Observable<Result<GPS>> getLocationInfo(@QueryMap Map<String, Object> map);

    @POST("/app/gps/getTrackPlayback")
    Observable<Result<RecorShow>> getTrackPlayback(@QueryMap Map<String, Object> map);

    @POST("/app/gps/queryAllUser")
    Observable<Result<DeviceLocation>> queryAllUser(@QueryMap Map<String, Object> map);

    @POST("/app/gps/equipmentAuthorize")
    Observable<Result<Object>> equipmentAuthorize(@QueryMap Map<String, Object> map);

    @POST("/app/gps/updateUploadTimek")
    Observable<Result<Object>> updateUploadTimek(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/queryTaskListData")
    Observable<Result<GPSInstall>> queryTaskListData(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/queryGpsCarInfo")
    Observable<Result<CarInfo>> queryGpsCarInfo(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/getImeiInfo")
    Observable<Result<DeviceInfo>> getImeiInfo(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/binding")
    Observable<Result<Object>> binding(@QueryMap Map<String, Object> map);

    @Multipart
    @POST()
    Observable<Result<Object>> uploadImg(@Url String url, @QueryMap Map<String, Object> map,
                                         @PartMap() Map<String, RequestBody> params);

    @POST("/app/auth/task/deleteImg")
    Observable<Result<Object>> deleteImg(@QueryMap Map<String, Object> map);


    @POST("/app/auth/task/queryAllImgByParams")
    Observable<Result<Loading>> queryAllImgByParams(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/queryMortgageInfo")
    Observable<Result<VehicleEvaluation>> queryMortgageInfo(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/updateImeiInfo")
    Observable<Result<ImeiInfo>> updateImeiInfo(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/teardown")
    Observable<Result<Object>> teardown(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/completeTask")
    Observable<Result<Object>> completeTask(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/updateLoanAudiByBussinessId")
    Observable<Result<Object>> updateLoanAudiByBussinessId(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/queryEvalCarInfo")
    Observable<Result<com.hxyd.dyt.appraiser.modle.entity.CarInfo>> queryEvalCarInfo(@QueryMap Map<String, Object> map);

    @POST("/app/auth/task/updateEvalCarInfo")
    Observable<Result<Object>> updateEvalCarInfo(@QueryMap Map<String, Object> map);

    @POST( "/app/auth/task/saveCarEvaluation")
    Observable<Result<Object>> saveCarEvaluation(@QueryMap Map<String, Object> map);



}
