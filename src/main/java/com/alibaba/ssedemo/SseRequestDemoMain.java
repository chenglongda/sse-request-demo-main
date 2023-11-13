package com.alibaba.ssedemo;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class SseRequestDemoMain {

    public static void main(String[] args) {

        String url = "http://xxxxx:8080/api/llm/gc/translateGenerate";

        String token = "xxxxxxxxxxxxxxxxxxxxxxxxx";

        String requestBodyJsonStr = "{\n" +
                "  \"referenceData\": {\n" +
                "    \"contents\": [\n" +
                "      \"    总的来说，今年一季度我国城镇新增就业取得了较好的成绩，就业形势总体稳定。政府在促进就业方面采取了一系列措施，为就业提供了有利条件。同时，推出全国就业公共服务信息平台也将为就业服务工作提供更好的支持和保障。展望未来，我们有理由相信，我国就业形势将继续保持稳定，为经济社会发展提供有力支撑。\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"prompt\": \"toEnglish\"\n" +
                "}";

        try (SSEIterator sseIterator = new SSEIterator(url, token, requestBodyJsonStr)) {
            while (sseIterator.hasNext()) {
                SSEIterator.SseEventData event = sseIterator.next();
                System.out.println("Received event: " + event);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}