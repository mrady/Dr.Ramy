package ramymichealcenter.patient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mostafa on 10/14/2017.
 */

public class PostModel {

    @SerializedName("post_id")
    private Integer postId;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("photo_url")
    private String photoUrl;

    @SerializedName("creation_date")
    private String creationDate;
    /*------------------------------------------------*/

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    /*------------------------------------------------*/

    @Override
    public String toString() {
        return "PostModel{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

}
