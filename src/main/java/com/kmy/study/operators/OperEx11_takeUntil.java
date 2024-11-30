package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * takeUntil()
 * upstream -> downstream 에서
 * 조건이 참이 될떄까지 emit
 * Predicate를 평가 할 떄 사용한  데이터가 포함됨.
 *
 */
@Slf4j
public class OperEx11_takeUntil {
    public static void main(String[] args) throws InterruptedException{
        Flux.
                fromIterable(SampleData.btcTopPricesPerYear)
                .takeUntil(t -> t.getT2() > 20_000_000) // <- 해당 조건이 참이 될떄까지 실행.
                .subscribe(d -> log.info("#onNext : {}, {}", d.getT1(), d.getT2()));
/*
19:24:40.850 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2010, 565
19:24:40.852 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2011, 36094
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2012, 17425
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2013, 1405209
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2014, 1237182
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2015, 557603
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2016, 1111811
19:24:40.853 [main] INFO com.kmy.study.operators.OperEx11_takeUntil -- #onNext : 2017, 22483583 <- 평가 할때 사용한 데이터가 포함된다는 뜻
 */
    }
}
