package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * next
 * upstream -> downstream 에서
 * 첫번쨰 데이터만 emit, 만약 데이터가 emtpy면 downstream에 Mono.empty()
 *
 */
@Slf4j
public class OperEx12_next {
    public static void main(String[] args) throws InterruptedException{
        Flux.
                fromIterable(SampleData.btcTopPricesPerYear)
                .next()
                .subscribe(d -> log.info("#onNext : {}, {}", d.getT1(), d.getT2()));

/*
    19:36:23.021 [main] INFO com.kmy.study.operators.OperEx12_next -- #onNext : 2010, 565
*/
    }
}
