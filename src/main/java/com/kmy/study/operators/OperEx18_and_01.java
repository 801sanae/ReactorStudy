package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * and
 * Mono의 complete Signal과 파라미터로 입력된 publisher의 complete signal을 결합하여 Mono<Void>를 반환
 *
 */
@Slf4j
public class OperEx18_and_01 {
    public static void main(String[] args) throws InterruptedException{
        Mono
                .just("task1")
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(d->log.info("#Mono doOnNext : {}", d))
                .and(
                        Flux
                                .just("task2", "task3")
                                .delayElements(Duration.ofMillis(600))
                                .doOnNext(d->log.info("#flux doOnNext : {}", d)))
                .subscribe(
                        d->log.info("#onNext : {}", d),
                        e->log.error("#onError: {}", e),
                        ()->log.info("#onComplete()")   // upstream에서 전달된 데이터가 없고, Complete signal만 받는다.
                );

        Thread.sleep(5000);
    }
}
