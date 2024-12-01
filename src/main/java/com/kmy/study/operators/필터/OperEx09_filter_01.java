package com.kmy.study.operators.필터;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *  Flux.filter
 *  Stream으로 넘겨 받은 데이터 조건부 필터링. range
 */
@Slf4j
public class OperEx09_filter_01 {
    public static void main(String[] args) {
        Flux
                .range(1,7)
                .filter(num-> num % 2 ==0)
                .subscribe(data->log.info("# onNext : {}", data));

    }

}
