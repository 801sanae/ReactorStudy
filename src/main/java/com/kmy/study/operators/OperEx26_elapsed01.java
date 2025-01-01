package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * elapsed를 쓰면
 * Tuple<long, T>로 리턴.
 */
@Slf4j
public class OperEx26_elapsed01 {
    public static void main(String[] args) throws InterruptedException{
        Flux.range(1,6)
                .delayElements(Duration.ofSeconds(1)) // emit을 1초씩
                .elapsed()
                .subscribe(data -> log.info("doOnNext : {}, time : {}", data.getT2(), data.getT1()));

        Thread.sleep(6000);
    }
}
