package com.kmy.study.operators.downstream처리;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * takeLast(n)
 * upstream -> downstream 에서
 * 인자(시간)로 받은 만큼만 가장 마지막에 emit된 데이터를 downstream에 emit한다.
 */
@Slf4j
public class OperEx11_takeLast {
    public static void main(String[] args) throws InterruptedException{
        Flux.
                fromIterable(SampleData.btcTopPricesPerYear)
                .takeLast(2) // 가잠 마지막 emit
                .subscribe(d -> log.info("#onNext : {}, {}", d.getT1(), d.getT2()));

    }
}
