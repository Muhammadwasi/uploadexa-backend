package com.upxa.app.dto.response;

public class Response {
    private int status;

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                '}';
    }
}
