package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * packageName    : com.kmy.study.operators
 * fileName       : OperEx03_fromStream
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * Mono.fromStream()
 * Stream에 포함된 데이터를 emit하는 flux 생성한다.
 * cancel, error, complete 시에 닫히며, Stream 특성상 재사용 X
 *
 */
@Slf4j
public class OperEx03_fromStream {
    public static void main(String[] args) {
        Flux
                .fromStream(SampleData.coinNames.stream())
                .filter(coinName -> coinName.equals("BTC") || coinName.equals("ETH"))
                .subscribe(data -> log.info("Received coin name : {}", data));
    }
}
