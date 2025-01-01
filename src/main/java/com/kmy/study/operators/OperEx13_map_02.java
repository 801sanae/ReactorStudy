package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * map( n->n )
 * upstream -> downstream 에서
 * emit한 데이터를 변환하여 emit한다.
 */
@Slf4j
public class OperEx13_map_02 {

    static final double buyPrice = 50_000_000;

    public static void main(String[] args) {

        Flux
                .fromIterable(SampleData.btcTopPricesPerYear)
                .filter(f->f.getT1() == 2021)
                .doOnNext(d->log.info("doOnNext ::{}", d))
                .map(d-> calc(buyPrice, d.getT2()))
                .subscribe(f->log.info("# onNext : {}%", f));

    }

    private static double calc(final double buyPrices, Long topPrice){
        return (topPrice - buyPrices) / buyPrices * 100;
    }
}
