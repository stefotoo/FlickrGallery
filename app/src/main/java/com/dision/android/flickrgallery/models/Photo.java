package com.dision.android.flickrgallery.models;

import com.google.gson.annotations.SerializedName;

public class Photo {

    // variables
    @SerializedName("id")
    private String id;
    @SerializedName("secret")
    private String caption;
    @SerializedName("url_m")
    private String url;
    @SerializedName("owner")
    private String owner;
    @SerializedName("title")
    private String description;

    // constructor
    public Photo() {
        // default
    }

    // methods
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return caption;
    }
}
