
package com.mycode.sample.soundcast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {
    private Boolean isDownloaded = false;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("music_file")
    @Expose
    private MusicFile musicFile;
    @SerializedName("thumbnail_file")
    @Expose
    private ThumbnailFile thumbnailFile;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }
    public Boolean getDownloaded() { return isDownloaded; }

    public void setDownloaded(Boolean downloaded) { isDownloaded = downloaded; }
    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public MusicFile getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(MusicFile musicFile) {
        this.musicFile = musicFile;
    }

    public ThumbnailFile getThumbnailFile() {
        return thumbnailFile;
    }

    public void setThumbnailFile(ThumbnailFile thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

}
