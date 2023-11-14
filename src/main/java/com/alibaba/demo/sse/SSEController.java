package com.alibaba.demo.sse;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * demo: 
 * @Date 2023/11/13 16:41
 */
@RestController
public class SSEController {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    @RequestMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse(@RequestBody(required = false) String body) {
        System.out.println("Request: " + body);
        SseEmitter emitter = new SseEmitter();
        executorService.execute(() -> callSseService(emitter, body));
        // 设置超时时间和异常处理
        emitter.onTimeout(emitter::complete);
        emitter.onError((e) -> emitter.complete());
        return emitter; 
    }

    private static void callSseService(SseEmitter emitter, String body) {
        try (SSEIterator sseIterator = new SSEIterator(SSEContents.DEMO_URL, SSEContents.DEMO_TOKEN, StringUtils.defaultIfEmpty(body, SSEContents.DEMO_REQUEST_BODY_JSON_STR))) {
            while (sseIterator.hasNext()) {
                SSEIterator.SseEventData event = sseIterator.next();
                if (event != null && event.id() != null) {
                    SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event().id(event.id()).data(event.data(), MediaType.APPLICATION_JSON);
                    emitter.send(sseEventBuilder);
                    System.out.println("Received id: " + JSON.toJSONString(event.id()) + " event:" + JSON.toJSONString(event.event())
                            + " data:" + JSON.toJSONString(event.data()));
                }
            }
            emitter.complete();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}
