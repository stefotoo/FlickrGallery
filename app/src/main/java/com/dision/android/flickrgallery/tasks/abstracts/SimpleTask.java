package com.dision.android.flickrgallery.tasks.abstracts;

import android.os.AsyncTask;

public abstract class SimpleTask<P, E> extends AsyncTask<Void, P, E> {

    // variables
    private SimpleCallback<E> mCallback;
    private boolean mIsWorking;

    // callbacks
    public static interface SimpleCallback<E> {
        void onStart();
        void onComplete(E res);
    }

    public SimpleTask(SimpleCallback<E> callback) {
        super();

        this.mCallback = callback;
        this.mIsWorking = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mIsWorking = true;

        if (mCallback != null) {
            mCallback.onStart();
        }
    }

    @Override
    protected void onPostExecute(E e) {
        super.onPostExecute(e);

        mIsWorking = false;

        if (mCallback != null) {
            mCallback.onComplete(e);
        }
    }

    public boolean isWorking() {
        return mIsWorking;
    }
}