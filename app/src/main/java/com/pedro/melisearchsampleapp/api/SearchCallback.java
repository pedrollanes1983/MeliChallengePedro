package com.pedro.melisearchsampleapp.api;

public interface SearchCallback {
    public void onResponse();
    public void onFailure(Throwable t);
}
