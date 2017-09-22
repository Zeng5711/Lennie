package com.hxyd.dyt.accountManager.modle.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by win7 on 2017/5/23.
 */

public class JingZhengu {

    /**
     * TrimId : 113181
     * Msrp : 13.73
     * DealerPrice :
     * HotStyle : {"1":"骐达TIIDA 2008 款 1.6L 手动 智能型","2":"骐达TIIDA 2011 款 1.6L CVT XL智能型","3":"骐达TIIDA 2011 款 1.6L CVT XE舒适型","4":"骐达TIIDA 2013 款 1.6L CVT XL酷咖版","5":"骐达TIIDA 2008 款 1.6L 自动 智能型","6":"骐达TIIDA 2014 款 1.6L CVT 舒适型XE","7":"骐达TIIDA 2014 款 1.6L CVT 智能型XL","8":"骐达TIIDA 2008 款 1.6L 自动 时尚型","9":"骐达TIIDA 2011 款 1.6L CVT XL-LUXURY豪华型","10":"骐达TIIDA 2012 款 1.6T CVT XV致酷版"}
     * C2BPrices : 6.33
     * PriceTendency : [{"2017-06":"6.25","2017-09":"6.07","2017-12":"5.94","2018-03":"5.78","2018-06":"5.61"}]
     * YouXinPai : [{"PublishTime":"2016-06","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"优信拍","TradeType":"拍卖","Price":"7.43","BuyCarDate":"2015-04","DrivingMileage":"2.00","CarColor":"白色","CityName":"上海"}]
     * YxpAvgPrice : 7.43
     * AutoHome : [{"PublishTime":"2017-05","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.30","BuyCarDate":"2014-01","DrivingMileage":"40000","CarColor":"香槟色","CityName":"淄博"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.80","BuyCarDate":"2014-08","DrivingMileage":"50000","CarColor":"红色","CityName":"徐州"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.80","BuyCarDate":"2015-04","DrivingMileage":"20000","CarColor":"白色","CityName":"宁波"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.80","BuyCarDate":"2014-04","DrivingMileage":"34000","CarColor":"白色","CityName":"深圳"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.28","BuyCarDate":"2015-01","DrivingMileage":"35000","CarColor":"白色","CityName":"温州"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.88","BuyCarDate":"2015-03","DrivingMileage":"38000","CarColor":"红色","CityName":"三明"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.88","BuyCarDate":"2015-03","DrivingMileage":"38000","CarColor":"红色","CityName":"长沙"},{"PublishTime":"2017-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"10.37","BuyCarDate":"2015-03","DrivingMileage":"48700","CarColor":"白色","CityName":"广州"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.80","BuyCarDate":"2015-04","DrivingMileage":"18000","CarColor":"红色","CityName":"苏州"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.30","BuyCarDate":"2014-06","DrivingMileage":"46000","CarColor":"银灰色","CityName":"南宁"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.30","BuyCarDate":"2014-06","DrivingMileage":"46000","CarColor":"银灰色","CityName":"南宁"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.98","BuyCarDate":"2015-01","DrivingMileage":"20000","CarColor":"其他","CityName":"常州"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.30","BuyCarDate":"2014-06","DrivingMileage":"46000","CarColor":"银灰色","CityName":"南宁"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.30","BuyCarDate":"2014-06","DrivingMileage":"46000","CarColor":"银灰色","CityName":"南宁"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.38","BuyCarDate":"2015-04","DrivingMileage":"18000","CarColor":"红色","CityName":"苏州"},{"PublishTime":"2017-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.38","BuyCarDate":"2014-06","DrivingMileage":"30000","CarColor":"红色","CityName":"乌鲁木齐"},{"PublishTime":"2017-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.50","BuyCarDate":"2014-11","DrivingMileage":"46000","CarColor":"白色","CityName":"包头"},{"PublishTime":"2017-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.90","BuyCarDate":"2016-02","DrivingMileage":"21000","CarColor":"银灰色","CityName":"厦门"},{"PublishTime":"2017-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.90","BuyCarDate":"2016-02","DrivingMileage":"10000","CarColor":"银灰色","CityName":"厦门"},{"PublishTime":"2017-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.68","BuyCarDate":"2015-05","DrivingMileage":"51000","CarColor":"白色","CityName":"无锡"},{"PublishTime":"2017-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.50","BuyCarDate":"2015-10","DrivingMileage":"22000","CarColor":"黄色","CityName":"包头"},{"PublishTime":"2017-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.70","BuyCarDate":"2015-11","DrivingMileage":"20000","CarColor":"白色","CityName":"沈阳"},{"PublishTime":"2017-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.98","BuyCarDate":"2015-11","DrivingMileage":"20000","CarColor":"白色","CityName":"沈阳"},{"PublishTime":"2016-12","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.68","BuyCarDate":"2014-05","DrivingMileage":"22900","CarColor":"红色","CityName":"长沙"},{"PublishTime":"2016-12","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.68","BuyCarDate":"2014-05","DrivingMileage":"23000","CarColor":"红色","CityName":"长沙"},{"PublishTime":"2016-12","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.88","BuyCarDate":"2014-08","DrivingMileage":"32000","CarColor":"深灰色","CityName":"大连"},{"PublishTime":"2016-11","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.25","BuyCarDate":"2014-09","DrivingMileage":"29000","CarColor":"白色","CityName":"沈阳"},{"PublishTime":"2016-11","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"10.78","BuyCarDate":"2015-05","DrivingMileage":"20000","CarColor":"黑色","CityName":"常州"},{"PublishTime":"2016-04","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.68","BuyCarDate":"2014-01","DrivingMileage":"46000","CarColor":"","CityName":"南宁"},{"PublishTime":"2016-03","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.88","BuyCarDate":"2014-06","DrivingMileage":"20000","CarColor":"","CityName":"东莞"},{"PublishTime":"2016-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.60","BuyCarDate":"2014-09","DrivingMileage":"10000","CarColor":"","CityName":"温州"},{"PublishTime":"2016-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.90","BuyCarDate":"2015-06","DrivingMileage":"8000","CarColor":"","CityName":"沈阳"},{"PublishTime":"2016-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.88","BuyCarDate":"2014-03","DrivingMileage":"28000","CarColor":"","CityName":"深圳"},{"PublishTime":"2016-01","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.80","BuyCarDate":"2014-04","DrivingMileage":"23000","CarColor":"","CityName":"深圳"},{"PublishTime":"2015-11","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.68","BuyCarDate":"2014-03","DrivingMileage":"17000","CarColor":"","CityName":"武汉"},{"PublishTime":"2015-10","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.90","BuyCarDate":"2014-02","DrivingMileage":"20000","CarColor":"","CityName":"大同"},{"PublishTime":"2015-10","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"8.98","BuyCarDate":"2013-11","DrivingMileage":"19000","CarColor":"","CityName":"沈阳"},{"PublishTime":"2015-10","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"9.00","BuyCarDate":"2014-01","DrivingMileage":"13000","CarColor":"","CityName":"太原"},{"PublishTime":"2015-09","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"汽车之家","TradeType":"零售","Price":"10.50","BuyCarDate":"2014-06","DrivingMileage":"21000","CarColor":"","CityName":"佛山"}]
     * AutoAvgPrice : 9.44
     * GuaZhi : [{"PublishTime":"2017-02","CarName":"日产骐达 2014款 1.6L CVT 豪华型XV","Source":"瓜子","TradeType":"零售","Price":"8.50","BuyCarDate":"2014-11","DrivingMileage":"26000","CarColor":"","CityName":"北京"}]
     * GuaZhiAvgPrice : 8.50
     * PtvUrl : http://www.jingzhengu.com/gujia/style-113181/5-1997-02-23/9/902
     */

    private String TrimId;
    private String Msrp;
    private String DealerPrice;
    private JsonObject HotStyle;
    private String C2BPrices;
    private String YxpAvgPrice;
    private String AutoAvgPrice;
    private String GuaZhiAvgPrice;
    private String PtvUrl;
    private JsonArray PriceTendency;
    private List<YouXinPaiBean> YouXinPai;
    private List<AutoHomeBean> AutoHome;
    private List<GuaZhiBean> GuaZhi;

    public String getTrimId() {
        return TrimId;
    }

    public void setTrimId(String TrimId) {
        this.TrimId = TrimId;
    }

    public String getMsrp() {
        return Msrp;
    }

    public void setMsrp(String Msrp) {
        this.Msrp = Msrp;
    }

    public String getDealerPrice() {
        return DealerPrice;
    }

    public void setDealerPrice(String DealerPrice) {
        this.DealerPrice = DealerPrice;
    }

    public JsonObject getHotStyle() {
        return HotStyle;
    }

    public void setHotStyle(JsonObject HotStyle) {
        this.HotStyle = HotStyle;
    }

    public String getC2BPrices() {
        return C2BPrices;
    }

    public void setC2BPrices(String C2BPrices) {
        this.C2BPrices = C2BPrices;
    }

    public String getYxpAvgPrice() {
        return YxpAvgPrice;
    }

    public void setYxpAvgPrice(String YxpAvgPrice) {
        this.YxpAvgPrice = YxpAvgPrice;
    }

    public String getAutoAvgPrice() {
        return AutoAvgPrice;
    }

    public void setAutoAvgPrice(String AutoAvgPrice) {
        this.AutoAvgPrice = AutoAvgPrice;
    }

    public String getGuaZhiAvgPrice() {
        return GuaZhiAvgPrice;
    }

    public void setGuaZhiAvgPrice(String GuaZhiAvgPrice) {
        this.GuaZhiAvgPrice = GuaZhiAvgPrice;
    }

    public String getPtvUrl() {
        return PtvUrl;
    }

    public void setPtvUrl(String PtvUrl) {
        this.PtvUrl = PtvUrl;
    }

    public JsonArray getPriceTendency() {
        return PriceTendency;
    }

    public void setPriceTendency(JsonArray PriceTendency) {
        this.PriceTendency = PriceTendency;
    }

    public List<YouXinPaiBean> getYouXinPai() {
        return YouXinPai;
    }

    public void setYouXinPai(List<YouXinPaiBean> YouXinPai) {
        this.YouXinPai = YouXinPai;
    }

    public List<AutoHomeBean> getAutoHome() {
        return AutoHome;
    }

    public void setAutoHome(List<AutoHomeBean> AutoHome) {
        this.AutoHome = AutoHome;
    }

    public List<GuaZhiBean> getGuaZhi() {
        return GuaZhi;
    }

    public void setGuaZhi(List<GuaZhiBean> GuaZhi) {
        this.GuaZhi = GuaZhi;
    }

    public static class YouXinPaiBean {
        /**
         * PublishTime : 2016-06
         * CarName : 日产骐达 2014款 1.6L CVT 豪华型XV
         * Source : 优信拍
         * TradeType : 拍卖
         * Price : 7.43
         * BuyCarDate : 2015-04
         * DrivingMileage : 2.00
         * CarColor : 白色
         * CityName : 上海
         */

        private String PublishTime;
        private String CarName;
        private String Source;
        private String TradeType;
        private String Price;
        private String BuyCarDate;
        private String DrivingMileage;
        private String CarColor;
        private String CityName;

        public String getPublishTime() {
            return PublishTime;
        }

        public void setPublishTime(String PublishTime) {
            this.PublishTime = PublishTime;
        }

        public String getCarName() {
            return CarName;
        }

        public void setCarName(String CarName) {
            this.CarName = CarName;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getTradeType() {
            return TradeType;
        }

        public void setTradeType(String TradeType) {
            this.TradeType = TradeType;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getBuyCarDate() {
            return BuyCarDate;
        }

        public void setBuyCarDate(String BuyCarDate) {
            this.BuyCarDate = BuyCarDate;
        }

        public String getDrivingMileage() {
            return DrivingMileage;
        }

        public void setDrivingMileage(String DrivingMileage) {
            this.DrivingMileage = DrivingMileage;
        }

        public String getCarColor() {
            return CarColor;
        }

        public void setCarColor(String CarColor) {
            this.CarColor = CarColor;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }

    public static class AutoHomeBean {
        /**
         * PublishTime : 2017-05
         * CarName : 日产骐达 2014款 1.6L CVT 豪华型XV
         * Source : 汽车之家
         * TradeType : 零售
         * Price : 8.30
         * BuyCarDate : 2014-01
         * DrivingMileage : 40000
         * CarColor : 香槟色
         * CityName : 淄博
         */

        private String PublishTime;
        private String CarName;
        private String Source;
        private String TradeType;
        private String Price;
        private String BuyCarDate;
        private String DrivingMileage;
        private String CarColor;
        private String CityName;

        public String getPublishTime() {
            return PublishTime;
        }

        public void setPublishTime(String PublishTime) {
            this.PublishTime = PublishTime;
        }

        public String getCarName() {
            return CarName;
        }

        public void setCarName(String CarName) {
            this.CarName = CarName;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getTradeType() {
            return TradeType;
        }

        public void setTradeType(String TradeType) {
            this.TradeType = TradeType;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getBuyCarDate() {
            return BuyCarDate;
        }

        public void setBuyCarDate(String BuyCarDate) {
            this.BuyCarDate = BuyCarDate;
        }

        public String getDrivingMileage() {
            return DrivingMileage;
        }

        public void setDrivingMileage(String DrivingMileage) {
            this.DrivingMileage = DrivingMileage;
        }

        public String getCarColor() {
            return CarColor;
        }

        public void setCarColor(String CarColor) {
            this.CarColor = CarColor;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }

    public static class GuaZhiBean {
        /**
         * PublishTime : 2017-02
         * CarName : 日产骐达 2014款 1.6L CVT 豪华型XV
         * Source : 瓜子
         * TradeType : 零售
         * Price : 8.50
         * BuyCarDate : 2014-11
         * DrivingMileage : 26000
         * CarColor :
         * CityName : 北京
         */

        private String PublishTime;
        private String CarName;
        private String Source;
        private String TradeType;
        private String Price;
        private String BuyCarDate;
        private String DrivingMileage;
        private String CarColor;
        private String CityName;

        public String getPublishTime() {
            return PublishTime;
        }

        public void setPublishTime(String PublishTime) {
            this.PublishTime = PublishTime;
        }

        public String getCarName() {
            return CarName;
        }

        public void setCarName(String CarName) {
            this.CarName = CarName;
        }

        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }

        public String getTradeType() {
            return TradeType;
        }

        public void setTradeType(String TradeType) {
            this.TradeType = TradeType;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getBuyCarDate() {
            return BuyCarDate;
        }

        public void setBuyCarDate(String BuyCarDate) {
            this.BuyCarDate = BuyCarDate;
        }

        public String getDrivingMileage() {
            return DrivingMileage;
        }

        public void setDrivingMileage(String DrivingMileage) {
            this.DrivingMileage = DrivingMileage;
        }

        public String getCarColor() {
            return CarColor;
        }

        public void setCarColor(String CarColor) {
            this.CarColor = CarColor;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }
    }
}
