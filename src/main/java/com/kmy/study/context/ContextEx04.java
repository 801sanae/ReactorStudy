package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx04
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * ContextEx04.java
 * Context의 특징
 * #1 Context는 subscribe별 하나의 context가 연결된다.
 * 동일한 key에 값을 중복해서 저장하면 체인에서 가장 위쪽에 위치한 contextWrite가 overwirte된다.
 */
@Slf4j
public class ContextEx04 {

    public static void main(String[] args) throws InterruptedException{
        final String key1 = "company";

        Mono<String> mono = Mono.deferContextual(ctx -> Mono.just("Company : " + " " + ctx.get(key1))).publishOn(Schedulers.parallel());

        mono.contextWrite(context -> context.put(key1, "Google"))
                .subscribe(data -> log.info("#1 subscribe onNext : {}", data));

        mono.contextWrite(context -> context.put(key1, "Microsoft"))
                .subscribe(data -> log.info("#2 subscribe onNext : {}", data));

        Thread.sleep(100L);
    }
}
