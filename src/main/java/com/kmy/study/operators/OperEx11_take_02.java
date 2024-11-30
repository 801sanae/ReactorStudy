package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * take(n)
 * upstream -> downstream 에서
 * 인자(시간)로 받은 만큼만 downstream에 emit한다.
 */
@Slf4j
public class OperEx11_take_02 {
    public static void main(String[] args) throws InterruptedException{
        Flux.interval(Duration.ofSeconds(1))
                .take(Duration.ofMillis(2500))
                .subscribe(d -> log.info("#onNext : {}", d));

        Thread.sleep(5000L);
    }
}
