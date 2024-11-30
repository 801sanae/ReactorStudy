package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * and
 * Mono의 complete Signal과 파라미터로 입력된 publisher의 complete signal을 결합하여 Mono<Void>를 반환
 *
 */
@Slf4j
public class OperEx18_and_02 {
    public static void main(String[] args) throws InterruptedException{
        restartApplicationServer()
                .and(restartDBServer())
                .subscribe(
                        d->log.info("#onNext : {}", d),
                        e->log.error("#onError: {}", e),
                        ()->log.info("All Servers are restarted successfully")   // upstream에서 전달된 데이터가 없고, Complete signal만 받는다.
                );

        Thread.sleep(5000);
    }

    public static Mono<String> restartApplicationServer(){
        return Mono.just("Application Server was restarted successfully.")
                .delayElement(Duration.ofSeconds(2))
                .doOnNext(log::info);
    }

    public static Publisher<String> restartDBServer(){
        return Mono.just("DB Server was restarted successfully.")
                .delayElement(Duration.ofSeconds(4))
                .doOnNext(log::info);
    }
}
