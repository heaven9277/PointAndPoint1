package com.example.zhw.piontandpiont2.Bean;

public class LocationBean {
    public int operateId;
    public String uuid;
    public double longitude;
    public double latitude;
    public LocationBean(int operateId,String uuid,double longitude,double latitude){
        this.operateId = operateId;
        this.uuid = uuid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
