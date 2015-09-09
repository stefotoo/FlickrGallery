package com.dision.android.flickrgallery.application;

import android.app.Application;

import com.dision.android.flickrgallery.rest.RestClient;

public class App extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        restClient = new RestClient();
    }

    public static RestClient getRestClient()
    {
        return restClient;
    }
}
