package com.alibaba.demo.sse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * 
 */
public class SSEIterator implements Iterator<SSEIterator.SseEventData>, AutoCloseable {
    private final HttpURLConnection connection;
    private final BufferedReader reader;
    private SseEventData sseEventData;

    public static class SseEventData {
        private String id;
        private String event;
        private String data;

        public String id() {
            return id;
        }

        public SseEventData setId(String id) {
            this.id = id;
            return this;
        }

        public String event() {
            return event;
        }

        public SseEventData setEvent(String event) {
            this.event = event;
            return this;
        }

        public String data() {
            return data;
        }

        public SseEventData setData(String data) {
            this.data = data;
            return this;
        }
    }

    public SSEIterator(String url, String basicToken, String requestBodyJsonStr) throws IOException {
        // 创建URL对象，指定SSE服务端点的URL
        URL sseUrl = new URL(url);
        // 打开连接
        connection = (HttpURLConnection) sseUrl.openConnection();
        // 设置请求方法为GET
        connection.setRequestMethod("POST");

        // 设置连接超时和读取超时
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        //请求头
        connection.setRequestProperty("Accept", "text/event-stream");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + basicToken);

        // 连接
        connection.setDoOutput(true);
        connection.connect();

        //写请求体
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBodyJsonStr.getBytes(StandardCharsets.UTF_8));
        outputStream.close();

        // 初始化事件流读取器
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


        fetchEventData();
    }

    @Override
    public boolean hasNext() {
        return sseEventData != null;
    }

    private void fetchEventData() {

        SseEventData a;
        try {
            String line = reader.readLine();
            if (line == null) {
                sseEventData = null;
                return;
            }

            a = new SseEventData();

            while (setSseData(line, a)) {
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sseEventData = a;
    }

    private static boolean setSseData(String line, SseEventData a) {
        if (line == null) {
            return false;
        }
        if (line.startsWith("id:")) {
            a.setId(line.substring(3));
        } else if (line.startsWith("event:")) {
            a.setEvent(line.substring(6));
        } else if (line.startsWith("data:")) {
            a.setData(line.substring(5));
            return false;
        }
        return true;
    }

    @Override
    public SseEventData next() {
        SseEventData event = sseEventData;

        fetchEventData();

        return event;
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignore) {
            }
        }
        if (connection != null) {
            connection.disconnect();
        }
    }

}
