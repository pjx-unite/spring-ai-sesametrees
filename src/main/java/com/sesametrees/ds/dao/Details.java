package com.sesametrees.ds.dao;

public class Details {

    private String message;

    public String getMessage() {
        return "https://localhost:8080/files/" + message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
