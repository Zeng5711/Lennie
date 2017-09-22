package com.hxyd.dyt.accountManager.modle.entity;

import io.realm.RealmObject;

/**
 * Created by win7 on 2017/3/9.
 */

public class ImageInfo {

    /**
     * imageId : 8210
     * loanId : null
     * name : ff2
     * category : 92
     * thumbnailUrl : /app/image/imgRead?path=/filestorage/20170308/sys/img/20170308104121494_s.jpg
     * originalUrl : /app/image/imgRead?path=/filestorage/20170308/sys/img/20170308104121494.jpg
     * isValid : 1
     * createdBy : sys
     * updatedBy : null
     */

    private String imageId="";
    private String loanId="";
    private String name="";
    private String category="";
    private String thumbnailUrl="";
    private String originalUrl="";
    private String isValid="";
    private String createdBy="";
    private String updatedBy="";

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
