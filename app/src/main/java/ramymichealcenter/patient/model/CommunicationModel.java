package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 9/12/2017.
 */

public class CommunicationModel {

    @SerializedName("working_id")
    private Integer workingId;
    @SerializedName("communication_id")
    private Integer communicationId;

    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    @SerializedName("facebook_1")
    private String facebook_1;
    @SerializedName("facebook_2")
    private String facebook_2;
    @SerializedName("facebook_3")
    private String facebook_3;

    @SerializedName("twitter")
    private String twitter;
    @SerializedName("instagram")
    private String instagram;

    /*----------------------*/

    public Integer getWorkingId() {
        return workingId;
    }

    public void setWorkingId(Integer workingId) {
        this.workingId = workingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook_1() {
        return facebook_1;
    }

    public void setFacebook_1(String facebook_1) {
        this.facebook_1 = facebook_1;
    }

    public String getFacebook_2() {
        return facebook_2;
    }

    public void setFacebook_2(String facebook_2) {
        this.facebook_2 = facebook_2;
    }

    public String getFacebook_3() {
        return facebook_3;
    }

    public void setFacebook_3(String facebook_3) {
        this.facebook_3 = facebook_3;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Integer getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(Integer communicationId) {
        this.communicationId = communicationId;
    }

    /*----------------------*/

    @Override
    public String toString() {
        return "CommunicationModel{" +
                "workingId=" + workingId +
                ", communicationId=" + communicationId +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", facebook_1='" + facebook_1 + '\'' +
                ", facebook_2='" + facebook_2 + '\'' +
                ", facebook_3='" + facebook_3 + '\'' +
                ", twitter='" + twitter + '\'' +
                ", instagram='" + instagram + '\'' +
                '}';
    }
}
