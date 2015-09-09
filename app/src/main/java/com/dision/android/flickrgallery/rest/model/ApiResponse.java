package com.dision.android.flickrgallery.rest.model;

import com.dision.android.flickrgallery.models.PhotoGallery;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    // variables
    @SerializedName("photos")
    private PhotoGallery photoGallery;
    @SerializedName("stat")
    private String status;

    public ApiResponse() {
        // default
    }

    // methods
    public PhotoGallery getPhotoGallery() {
        return photoGallery;
    }

    public void setPhotoGallery(PhotoGallery photoGallery) {
        this.photoGallery = photoGallery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
