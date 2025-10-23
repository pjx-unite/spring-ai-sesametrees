package com.sesametrees.ds.dao;

public class Details {

    private String message;

    public String getMessage() {
        return "https://573d19cfb01b.ngrok-free.app/files/" + message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
