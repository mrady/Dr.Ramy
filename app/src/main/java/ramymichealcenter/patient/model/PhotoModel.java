package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 9/4/2017.
 */

public class PhotoModel {

    @SerializedName("photo_id")
    private Integer photoId;

    @SerializedName(value = "photo_url", alternate = "url")
    private String photoUrl;

    /*--------------------------------------------*/
    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /*--------------------------------------------*/
    @Override
    public String toString() {
        return "{" +
                "photoId=" + photoId +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
