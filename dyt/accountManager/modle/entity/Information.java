package com.hxyd.dyt.accountManager.modle.entity;

import java.util.List;

/**
 * Created by win7 on 2017/4/20.
 */

public class Information {


    private List<InformationItem> otherInformation;
    private List<InformationItem> driverLicense;//
    private List<InformationItem> vehicleInformation;
    private List<InformationItem> registration;//
    private List<InformationItem> drivingLicense;//
    private List<InformationItem> identifyCard;

    public List<InformationItem> getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(List<InformationItem> otherInformation) {
        this.otherInformation = otherInformation;
    }

    public List<InformationItem> getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(List<InformationItem> driverLicense) {
        this.driverLicense = driverLicense;
    }

    public List<InformationItem> getVehicleInformation() {
        return vehicleInformation;
    }

    public void setVehicleInformation(List<InformationItem> vehicleInformation) {
        this.vehicleInformation = vehicleInformation;
    }

    public List<InformationItem> getRegistration() {
        return registration;
    }

    public void setRegistration(List<InformationItem> registration) {
        this.registration = registration;
    }

    public List<InformationItem> getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(List<InformationItem> drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<InformationItem> getIdentifyCard() {
        return identifyCard;
    }

    public void setIdentifyCard(List<InformationItem> identifyCard) {
        this.identifyCard = identifyCard;
    }
}
