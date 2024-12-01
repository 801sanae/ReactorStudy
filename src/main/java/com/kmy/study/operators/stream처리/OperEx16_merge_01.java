package com.kmy.study.operators.stream처리;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;


/**
 * merge
 * 모든 publisher가 subscrible된다.
 * publisher의 seq에서 emit된 데이터가 동시에 emit 된다. (인터리빙 방식)
 * emit된 시간 순서대로 merge한다.
 */
@Slf4j
public class OperEx16_merge_01 {

    public static void main(String[] args) throws InterruptedException{
        Flux
                .merge(
                        Flux.just(1,2,3,4).delayElements(Duration.ofMillis(300L)),
                        Flux.just(5,6,7).delayElements(Duration.ofMillis(500L)))
                .subscribe(d->log.info("# onNext : {}", d));

//        3,6,9,12 -> 1,2,3,4
//        5,10,15 -> 5,6,7

        Thread.sleep(2000L);

/*
20:38:38.945 [parallel-1] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 1
20:38:39.145 [parallel-2] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 5
20:38:39.255 [parallel-3] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 2
20:38:39.556 [parallel-5] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 3
20:38:39.648 [parallel-4] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 6
20:38:39.857 [parallel-6] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 4
20:38:40.151 [parallel-7] INFO com.kmy.study.operators.OperEx16_merge -- # onNext : 7
 */
    }
}
