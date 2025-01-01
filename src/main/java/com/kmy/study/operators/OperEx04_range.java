package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * packageName    : com.kmy.study.operators
 * fileName       : OperEx04_range
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * Mono.range()
 * range(start, count)
 * start, count : start index, count of numbers to emit
 *
 */
@Slf4j
public class OperEx04_range {
    public static void main(String[] args) {

        Flux
                // #1 5부터 1씩 증가한 숫자 10개 emit
                .range(5,10)
                .subscribe(data -> log.info("Received data : {}", data));

        Flux
                // #2 7부터 5개의 숫자 emit
                .range(7, 5)
                .map(idx -> SampleData.btcTopPricesPerYear.get(idx))
                .subscribe(data -> log.info("Received data : {}'s {}", data.getT1(), data.getT2()));

    }
}
