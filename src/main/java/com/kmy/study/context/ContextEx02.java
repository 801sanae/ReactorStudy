package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx02
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */
@Slf4j
public class ContextEx02 {
    public static void main(String[] args) throws InterruptedException{
        final String key1 = "company";
        final String key2 = "firstName";
        final String key3 = "lastName";

        Mono.deferContextual(contextView ->
                // #1 ContextView로 가져옴
                     Mono.just(contextView.get(key1) + ", " +contextView.get(key2) + ", " +contextView.get(key3)))
                    .publishOn(Schedulers.parallel())
                // #2 contextView 객체여야되는데 context지만 readonly가 변환해줌
                    .contextWrite(context -> context.putAll(Context.of(key2,"Steve", key3,"Jobs").readOnly()))
                    .contextWrite(context-> context.put(key1,"Google"))
                    .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(100L);
    }
}
