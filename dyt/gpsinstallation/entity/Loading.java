package com.hxyd.dyt.gpsinstallation.entity;

import java.util.List;

/**
 * Created by win7 on 2017/9/14.
 */

public class Loading {

    private List<ImgListBean> imgList;

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public static class ImgListBean {
        /**
         * image_name : positionImages_01
         * group_id : positionImages
         * url : app/common/captcha/task/readImg?fileId=59ba2d9c841e351c625fe6db
         */

        private String image_name;
        private String group_id;
        private String url;

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
