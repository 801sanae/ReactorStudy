package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * refCount
 * 구독 취소, 연결해제시 사용
 */
@Slf4j
public class OperEx32_publish_refCount {

    public static void main(String[] args) throws InterruptedException{
        Flux<Long> publisher = Flux.interval(Duration.ofMillis(500))
                .publish().autoConnect(1)
                .publish().refCount(1);

        Disposable disp = publisher.subscribe(data -> log.info("# subscribler 1: {}", data));

        Thread.sleep(2100L);
        disp.dispose();

        publisher.subscribe(data -> log.info("# subscribler 2: {}", data));

        Thread.sleep(2500L);
    }
}
