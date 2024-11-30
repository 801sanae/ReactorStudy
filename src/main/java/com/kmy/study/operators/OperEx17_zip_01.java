package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * zip
 * 각 publisher가 데이터를 하나씩 emit할 때까지 기다렸다가 결합
 */
@Slf4j
public class OperEx17_zip_01 {

    public static void main(String[] args) throws InterruptedException{
        Flux
                .zip(
                        //        3,6,9,12 -> 1,2,3,4
                        //        5,10,15 -> 5,6,7
                        Flux.just(1,2,3,4).delayElements(Duration.ofMillis(300L)),
                        Flux.just(5,6,7).delayElements(Duration.ofMillis(500L)))
                .subscribe(d->log.info("# onNext : {}", d));

//        Tuple2 객체로 subscirble로 전달

        Thread.sleep(2000L);
    }
}
