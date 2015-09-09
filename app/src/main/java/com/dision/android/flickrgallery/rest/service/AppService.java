package com.dision.android.flickrgallery.rest.service;

import com.dision.android.flickrgallery.rest.model.ApiResponse;

import retrofit.Callback;
import retrofit.http.GET;

public interface AppService {

    @GET("/?method=flickr.photos.getRecent&api_key=7e5fe66cd36ad764e85073c054a5f047&per_page=12&format=json&nojsoncallback=1&extras=url_m")
    void getRecentPhotos(Callback<ApiResponse> callback);
}
