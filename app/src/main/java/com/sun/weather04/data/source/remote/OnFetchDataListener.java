package com.sun.weather04.data.source.remote;

public interface OnFetchDataListener<T> {

    void onSuccess(T data);

    void onError(Exception e);
}
