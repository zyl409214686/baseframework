package com.quickdev.baseframework.network;

public interface ICallback <T>{

    void onResponse(String str, T result);

    void onFailure(String str, String string);
}
