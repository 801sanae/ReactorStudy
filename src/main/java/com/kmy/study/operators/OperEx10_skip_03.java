package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * skip(n)
 * Upstream에서 emit 된 데이터 중 파라미터로 받은 숫자(시간 지정)만큼 건너뛰고 DownStream으로 emit한다.
 */
@Slf4j
public class OperEx10_skip_03 {
    public static void main(String[] args){
        Flux
                .fromIterable(SampleData.btcTopPricesPerYear)
                .filter(tuple -> tuple.getT2() >= 20_000_000) // 2000 이상
                .skip(2) // 두개 스킵
                .subscribe(data->log.info("#onNext : {} ,{}", data.getT1(), data.getT2()));
/*
before :
19:11:50.508 [main] INFO com.kmy.study.operators.downstream처리.OperEx10_skip_03 -- #onNext : 2017 ,22483583
19:11:50.511 [main] INFO com.kmy.study.operators.downstream처리.OperEx10_skip_03 -- #onNext : 2020 ,22439002
19:11:50.511 [main] INFO com.kmy.study.operators.downstream처리.OperEx10_skip_03 -- #onNext : 2021 ,63364000
after :
19:12:09.454 [main] INFO com.kmy.study.operators.downstream처리.OperEx10_skip_03 -- #onNext : 2021 ,63364000

 */
    }
}
