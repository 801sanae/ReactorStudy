package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx03
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */
@Slf4j
public class ContextEx03 {

    public static void main(String[] args) throws InterruptedException{
        final String key1 = "company";
        final String key2 = "firstName";
        final String key3 = "lastName";

        Mono
                .deferContextual(contextView ->
                        // #1 java8 이상부터 optional 처리 가능
                        Mono.just(contextView.get(key1) + ", "
                                + contextView.getOrEmpty(key2).orElse("no firstName") + " "
                                + contextView.getOrDefault(key3, "no lastName"))
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "google"))
                .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(100L);
    }
}
