package com.scholarcoder.chat.server.transport;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String asStringPayload() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("CHAT/1.0 ").append(statusCode);

        if (!headers.isEmpty()) {
            responseBuilder.append("\n");
        }
        AtomicInteger headerCounter = new AtomicInteger();
        headers.forEach((key, value) -> {
            headerCounter.getAndIncrement();
            responseBuilder.append(key).append(": ").append(value);
            if(headerCounter.get() != headers.size()) {
                responseBuilder.append("\n");
            }
        });

        if (body != null && !body.isEmpty()) {
            responseBuilder.append("\n\n");
            responseBuilder.append(body);
        }

        return responseBuilder.toString();
    }

    public static ChatResponse fromResponse(String response) {
        String[] responseLines = response.split("\n");

        ChatResponse chatResponse = new ChatResponse();
        setChatResponse_StatusLine(responseLines[0], chatResponse);

        boolean atBody = false;
        for (String property : responseLines) {
            if (property.equals(responseLines[0])) {
                continue;
            }

            if (beforeBodySection(property)) {
                atBody = true;
                continue;
            }
            if (atBody) {
                chatResponse.setBody(property);

            } else {
                extractResponseHeader(chatResponse, property);
            }
        }

        return chatResponse;
    }


    private static void extractResponseHeader(ChatResponse chatResponse, String property) {
        String[] headerLine = parseHeader(property);

        String headerName = headerLine[0].trim();
        String headerValue = headerLine[1].trim();
        chatResponse.addHeader(headerName, headerValue);

    }

    private static boolean beforeBodySection(String property) {
        return "".equals(property);
    }

    private static String[] parseHeader(String headerLine) {
        String headerName = extractHeaderName(headerLine);
        final String headerValueSelectorRegex = "(?<=\\w:).*";

        Pattern pattern = Pattern.compile(headerValueSelectorRegex);
        Matcher matcher = pattern.matcher(headerLine);

        String headerValue = null;
        while (matcher.find()) {
            headerValue = matcher.group(0);
        }
        return new String[]{headerName, headerValue};
    }

    private static String extractHeaderName(String headerLine) {
        return headerLine.split(":")[0];
    }


    private static void setChatResponse_StatusLine(String requestLine, ChatResponse chatResponse) {
        String[] requestLineTokens = requestLine.split(" ");
        String protocolVersion = requestLineTokens[0];
        String statusCode = requestLineTokens[1];
        String statusCodeDescription = requestLineTokens[2];

        chatResponse.setStatusCode(statusCode + " " + statusCodeDescription);
    }
}


