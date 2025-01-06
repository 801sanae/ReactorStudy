package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * publish
 *
 * 다수의 subscribler에게 flux를 멀티캐스팅하는 operator
 * Cold Sequence를 Hot Sequence로 동작하게 해준다.
 * subscrilber가 구독하면 upStream에서 emit된 데이터가 구독중인 모든 subscribler에게 멀티캐스팅된다.
 */
@Slf4j
public class OperEx31_publish01 {

    public static void main(String[] args) throws InterruptedException{
        ConnectableFlux<Integer> flux = Flux.range(1, 5).delayElements(Duration.ofMillis(300L)).publish();

        Thread.sleep(500L);
        flux.subscribe(data -> log.info("# subscriber1:{}", data));

        Thread.sleep(200L);
        flux.subscribe(data -> log.info("# subscriber2:{}", data));

        flux.connect();

        Thread.sleep(1000L);
        flux.subscribe(data -> log.info("# subscriber3:{}", data));

        Thread.sleep(2000L);

    }
}
/*
23:47:33.774 [parallel-1] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber1:1
23:47:33.778 [parallel-1] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber2:1
23:47:34.083 [parallel-2] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber1:2
23:47:34.083 [parallel-2] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber2:2
23:47:34.386 [parallel-3] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber1:3
23:47:34.387 [parallel-3] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber2:3

23:47:34.690 [parallel-4] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber1:4
23:47:34.690 [parallel-4] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber2:4
23:47:34.690 [parallel-4] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber3:4
23:47:34.996 [parallel-5] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber1:5
23:47:34.996 [parallel-5] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber2:5
23:47:34.996 [parallel-5] INFO com.kmy.study.operators.OperEx31_publish01 -- # subscriber3:5
 */