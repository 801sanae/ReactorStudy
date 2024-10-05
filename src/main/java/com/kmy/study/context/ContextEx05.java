package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx05
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * ContextEx05.java
 * Context의 특징
 * #2 contextWrite는 operator 체인에 맨 마지막에 위치한다.
 * context는 operator체인상 아래에서 위로 전파된다.
 */
@Slf4j
public class ContextEx05 {

    public static void main(String[] args) throws InterruptedException  {
        final String key1 = "company";
        final String key2 = "name";

        Mono.deferContextual(ctx -> Mono.just(ctx.get(key1)))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key2, "Bill"))
                .transformDeferredContextual((mono, ctx) -> mono.map(data -> data + ", " + ctx.getOrDefault(key2, "Steve")))
                .contextWrite(context -> context.put(key1, "Apple"))
                .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(100L);

    }
}
