package vodafone.consumer.IoT.batchDataLoadingService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="device_details")
public class IoTDataEntity {

    @Column(name="DateTime")
    private String datetime;
    @Id
    @Column(name="EventId")
    private long eventId;
    @Column(name="ProductId")
    private String productId;
    @Column(name="Latitude")
    private String latitude;
    @Column(name="Longitude")
    private String longitude;
    @Column(name="Battery")
    private String battery;
    @Column(name="Light")
    private String light;
    @Column(name="AirplaneMode")
    private String airplaneMode;
    @Column
    private String status;

    public IoTDataEntity() {
    }
/*
    public IoTDataEntity(String datetime, long eventId, String productId, double latitude, double longitude, double battery, String light, String airplaneMode) {
        this.datetime = datetime;
        this.eventId = eventId;
        this.productId = productId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
        this.light = light;
        this.airplaneMode = airplaneMode;
    }*/

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getAirplaneMode() {
        return airplaneMode;
    }

    public void setAirplaneMode(String airplaneMode) {
        this.airplaneMode = airplaneMode;
    }

    @Override
    public String toString() {
        return "IoTDataEntity{" +
                "datetime=" + datetime +
                ", eventId=" + eventId +
                ", productId='" + productId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", battery=" + battery +
                ", light='" + light + '\'' +
                ", airplaneMode='" + airplaneMode + '\'' +
                '}';
    }
}
