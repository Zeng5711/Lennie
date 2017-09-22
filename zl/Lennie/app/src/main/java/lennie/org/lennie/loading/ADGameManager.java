package lennie.org.lennie.loading;

import android.content.res.Resources;

//import com.pingan.anydoor.PAAnydoor;
//import com.pingan.anydoor.R;
//import com.pingan.anydoor.common.talkingdata.ADTDataManager;
//import com.pingan.anydoor.common.utils.JarUtils;
//import com.pingan.anydoor.common.utils.Tools;
//import com.pingan.anydoor.module.plugin.ADPluginManager;

import java.util.HashMap;

public class ADGameManager {

    public boolean isCloseWebView = false;

    public static String TAG = "ADGameManager";

    private final static String PLUGINID = "Pluginid";

    private final static String SCORE = "score";

    private final static String TIME = "Time";

    public final static int BG_COLOR = 0xddffffff;

    private String isShowNaviBar = "y";

//    private boolean isFOpen = false; // 是否由F打开

    private int mOpenMode  = 0;

    private static String rym_loading_talkdate_name = "";
    private static String rym_loading_talkdata_game_tip = "";
    private static String rym_loading_talkdata_game_tip_click = "";
    private static String rym_loading_talkdata_game_click = "";
    private static String rym_loading_talkdata_game_time = "";
    private static String rym_loading_talkdata_over_tip = "";
    private static String rym_loading_talkdata_over_click = "";
    private static String rym_loading_talkdata_game_close_buuton = "";


//    public void setIsFOpen(boolean is) {
//        isFOpen = is;
//    }

    public void setgLoadingMode(int openMode){
        mOpenMode = openMode;
    }

    public int getLoadingnMode(){
        return mOpenMode;
    }

//    public boolean getIsFOpen() {
//        return isFOpen;
//    }

    public void setIsShowNaviBar(String isShowNaviBar) {
        this.isShowNaviBar = isShowNaviBar;
    }

    public String getIsShowNaviBar() {
        return this.isShowNaviBar;
    }

    private static class SingletonHolder{
        private static ADGameManager INSTANCE = new ADGameManager();
    }

    public static ADGameManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ADGameManager() {
        init();
    }

    private void init() {
//        Resources resources = JarUtils.getResources();
//        if (resources!=null){
//            rym_loading_talkdate_name = resources.getString(R.string.rym_loading_talkdate_name);
//            rym_loading_talkdata_game_tip = resources.getString(R.string.rym_loading_talkdata_game_tip);
//            rym_loading_talkdata_game_tip_click = resources.getString(R.string.rym_loading_talkdata_game_tip_click);
//            rym_loading_talkdata_game_click = resources.getString(R.string.rym_loading_talkdata_game_click);
//            rym_loading_talkdata_game_time = resources.getString(R.string.rym_loading_talkdata_game_time);
//            rym_loading_talkdata_over_tip = resources.getString(R.string.rym_loading_talkdata_over_tip);
//            rym_loading_talkdata_over_click = resources.getString(R.string.rym_loading_talkdata_over_click);
//            rym_loading_talkdata_game_close_buuton = resources.getString(R.string.rym_loading_talkdata_game_close_buuton);
//        }


    }

    // 游戏引导显示
    public static void setGuideShowTD() {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
        // 菊花变成圆结束
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_game_tip,
//                map);
    }

    // 游戏引导点击
    public static void setGuideClickTD() {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_game_tip_click,
//                map);
    }

    // 游戏点击

    public static void setGameClickTD(final String mHitBall) {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        map.put(SCORE, mHitBall);
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_game_click,
//                map);
    }

    // 游戏时长

    public static void setGameTimeTD(final String time) {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        map.put(TIME, time);
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_game_time,
//                map);
    }

    // 游戏倒计时展示
    public static void setGameOverTD() {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_over_tip,
//                map);
    }

    // 游戏倒计时点击
    public static void setGameClickTD() {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_over_click,
//                map);
    }


    //游戏关闭按钮
    public static void setGameCloseButton() {
//        HashMap map = new HashMap<String, String>();
//        map.put(PLUGINID, ADPluginManager.getInstance().getClickedPlguinId());
//        ADTDataManager.setTalkingData(rym_loading_talkdate_name,
//                rym_loading_talkdata_game_close_buuton,
//                map);
    }

}
