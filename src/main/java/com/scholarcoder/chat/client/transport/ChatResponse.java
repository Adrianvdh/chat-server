package com.scholarcoder.chat.client.transport;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ChatResponse {

    private String statusCode;
    private Map<String, String> headers;

    private String body;

    public ChatResponse() {
        headers = new HashMap<>();
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addCookie(String name, String value) {
        headers.put("Set-Cookie", name + "=" + value);
    }
}


