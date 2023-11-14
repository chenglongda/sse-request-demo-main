package com.alibaba.demo.sse;

/**
 * @Date 2023/11/14 11:34
 */
public class SSEContents {

    public static final String DEMO_URL = "http://xx:xx/api/llm/gc/translateGenerate";
    
    public static final String DEMO_TOKEN = "Y2hxxxxxg=";
    
    public static final String DEMO_REQUEST_BODY_JSON_STR = "{\n" +
            "  \"referenceData\": {\n" +
            "    \"contents\": [\n" +
            "      \"    总的来说，今年一季度我国城镇新增就业取得了较好的成绩，就业形势总体稳定。政府在促进就业方面采取了一系列措施，为就业提供了有利条件。同时，推出全国就业公共服务信息平台也将为就业服务工作提供更好的支持和保障。展望未来，我们有理由相信，我国就业形势将继续保持稳定，为经济社会发展提供有力支撑。\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"prompt\": \"toEnglish\"\n" +
            "}";
}
