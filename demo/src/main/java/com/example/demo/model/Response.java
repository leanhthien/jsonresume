package com.example.demo.model;

import com.example.demo.entity.Product;

import java.util.List;

public class Response<T> {

    private String status;
    private T data;

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return status;
    }

    public void setMessage(String status) {
        this.status = status;
    }

    public T getData() { return data;}

    public void setData(T data) {
        this.data = data;
    }


}
