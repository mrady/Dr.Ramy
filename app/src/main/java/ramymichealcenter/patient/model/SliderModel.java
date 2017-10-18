package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 10/17/2017.
 */

public class SliderModel {

    @SerializedName("slider_id")
    private Integer sliderId;

    @SerializedName("photo_url")
    private String photoUrl;

    /*------------------------------*/

    public Integer getSliderId() {
        return sliderId;
    }

    public void setSliderId(Integer sliderId) {
        this.sliderId = sliderId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /*------------------------------*/

    @Override
    public String toString() {
        return "SliderModel{" +
                "sliderId=" + sliderId +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
