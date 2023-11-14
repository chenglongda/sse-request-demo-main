package com.alibaba.demo.sse;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.alibaba.demo.sse.SSEContents.*;


/**
 * @Date 2023/11/14 11:36
 */
public class SSETest {
    
    @Test
    public void test() {
        try (SSEIterator sseIterator = new SSEIterator(DEMO_URL, DEMO_TOKEN, DEMO_REQUEST_BODY_JSON_STR)) {
            while (sseIterator.hasNext()) {
                SSEIterator.SseEventData event = sseIterator.next();
                System.out.println("Received event: " + JSON.toJSONString(event.data()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
