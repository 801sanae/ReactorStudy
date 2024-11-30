package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * takeWhile
 * upstream -> downstream 에서
 * 조건이 참이 될떄까지 emit
 * Predicate를 평가 할 떄 사용한 데이터가 포함되지 않음.
 *
 */
@Slf4j
public class OperEx11_takeWhile {
    public static void main(String[] args) throws InterruptedException{
        Flux.
                fromIterable(SampleData.btcTopPricesPerYear)
                .takeWhile(t -> t.getT2() < 20_000_000) // <- 해당 조건이 참이 될떄까지 실행.
                .subscribe(d -> log.info("#onNext : {}, {}", d.getT1(), d.getT2()));
/*
19:31:02.343 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2010, 565
19:31:02.346 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2011, 36094
19:31:02.346 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2012, 17425
19:31:02.346 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2013, 1405209
19:31:02.346 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2014, 1237182
19:31:02.346 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2015, 557603
19:31:02.347 [main] INFO com.kmy.study.operators.OperEx11_takeWhile -- #onNext : 2016, 1111811
 */
    }
}
