package com.kmy.study.operators.upstreamRange;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * packageName    : com.kmy.study.operators
 * fileName       : OperEx02_fromIterable
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * Mono.fromIterable()
 * 리스트를 순차적으로 emitt
 *
 */
@Slf4j
public class OperEx02_fromIterable {

    public static void main(String[] args) {
        Flux
                .fromIterable(SampleData.coins)
                .subscribe(coin ->
                        log.info("Received coin명 : {}, 현재가 : {}", coin.getT1(), coin.getT2())
                );
    }
}
