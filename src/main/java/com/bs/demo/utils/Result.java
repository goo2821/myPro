package com.bs.demo.utils;

import com.google.gson.JsonObject;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public enum Result {

    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    USER_EXISTS(101, "The Username Already exists."),
    USER_NOT_FOUND(102, "The User Could Not Found.");

    private int code;
    private String msg;

    private Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        json.addProperty("code", this.code);
        json.addProperty("msg", this.msg);
        return json.toString();
    }

    public ResponseEntity<String> toResponse(HttpStatus status){
        return new ResponseEntity<String>(this.toString(), status);
    }
}
