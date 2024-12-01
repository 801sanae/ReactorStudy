package com.kmy.study.operators.필터;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *  Flux.filter
 *  Stream으로 넘겨 받은 데이터 조건부 필터링. fromIterable
 */
@Slf4j
public class OperEx09_filter_02 {
    public static void main(String[] args) {
        Flux
                .fromIterable(SampleData.btcTopPricesPerYear)
                .filter(tuple-> tuple.getT2() > 20_000_000)
                .subscribe(data->log.info("# {} : {}}", data.getT1(), data.getT2()));
    }

}
