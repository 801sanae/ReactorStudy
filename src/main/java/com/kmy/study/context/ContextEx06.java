package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx06
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */
/**
 * ContextEx06.java
 * Context의 특징
 * #3 innerCtx에서 저장한 key값은 outterCtx에서 전파 확인
 *
 */
@Slf4j
public class ContextEx06 {

    public static void main(String[] args) throws InterruptedException{
        String key1 = "company";

        Mono.just("Steve")
                .transformDeferredContextual((stringMono, ctx) -> ctx.get("role"))
                .flatMap(name -> Mono
                                    .deferContextual(ctx ->
                                                        Mono.just(ctx.get(key1) + ", " + name)
                                                                .transformDeferredContextual((innnerMono, innerCtx) ->
                                                                        innnerMono.map(data -> data + ", " + innerCtx.get("role") )
                                                                )
                                                            .contextWrite(context -> context.put("role", "CEO"))
                                    )
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.put(key1, "Apple"))
                .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(100L);


    }
}
