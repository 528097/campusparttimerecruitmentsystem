package com.example.campusparttimerecruitmentsystem.response;

import lombok.Data;

@Data
public class Response {
    private boolean success;
    private Object object;
    private String message;

    public Response(boolean success, Object object, String message) {
        this.success = success;
        this.object = object;
        this.message = message;
    }
}
