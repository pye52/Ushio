package com.kanade.ushio.api.responses;

public class BaseResponse {
    private String request;
    private int code;
    private String error;

    public String getRequest() {
        return request;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
