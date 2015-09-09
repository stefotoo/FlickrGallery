package com.dision.android.flickrgallery.rest;

import com.dision.android.flickrgallery.constants.ApiConstants;
import com.dision.android.flickrgallery.rest.service.AppService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {
    private static final String BASE_URL = ApiConstants.API_BASIC_URL;
    private AppService apiService;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(AppService.class);
    }

    public AppService getAppService() {
        return apiService;
    }

}
