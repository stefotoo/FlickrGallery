package com.dision.android.flickrgallery.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotoGallery {

    // variables
    @SerializedName("page")
    private long page;
    @SerializedName("pages")
    private long pages;
    @SerializedName("perpage")
    private long perPage;
    @SerializedName("total")
    private long total;
    @SerializedName("photo")
    private List<Photo> photos = new ArrayList<>();

    // constructor
    public PhotoGallery() {
        // default
    }

    // methods
    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
