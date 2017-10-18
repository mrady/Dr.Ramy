package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mostafa on 9/4/2017.
 */

public class ServiceModel {

    @SerializedName("service_id")
    private Integer serviceId;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("service_desc")
    private String serviceDesc;

    @SerializedName("allowBooking")
    private int allowBooking;

    @SerializedName("photo")
    private ArrayList<PhotoModel> photo = null;

    @SerializedName("working_day")
    private ArrayList<WorkingDayModel> workingDay = null;

    public ServiceModel(Integer serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    /*--------------------------------------------*/
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public ArrayList<PhotoModel> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<PhotoModel> photo) {
        this.photo = photo;
    }

    public ArrayList<WorkingDayModel> getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(ArrayList<WorkingDayModel> workingDay) {
        this.workingDay = workingDay;
    }

    public int getAllowBooking() {
        return allowBooking;
    }

    public void setAllowBooking(int allowBooking) {
        this.allowBooking = allowBooking;
    }

    /*--------------------------------------------*/
    @Override
    public String toString() {
        return serviceName;
    }
}
