package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * concat
 * publisher의 seq를 연결해서 순차적으로 emit해준다.
 * 먼저 입력된 publisher의 subscirble가 끝나면 "순차적"
 */
@Slf4j
public class OperEx15_concat_02 {
    public static void main(String[] args) {
        Flux
                .concat(
                        Flux.fromIterable(SampleData.viralVectorVaccines),
//                        Tuples.of(SampleData.CovidVaccine.AstraZeneca, 3_000_000),
//                        Tuples.of(SampleData.CovidVaccine.Janssen, 2_000_000)
                        Flux.fromIterable(SampleData.mRNAVaccines),
//                        Tuples.of(SampleData.CovidVaccine.Pfizer, 1_000_000),
//                        Tuples.of(SampleData.CovidVaccine.Moderna, 4_000_000)
                        Flux.fromIterable(SampleData.subunitVaccines)
//                        Tuples.of(SampleData.CovidVaccine.Novavax, 2_500_000)
                ).subscribe(d->log.info("#onNext :: {}", d));
    }

/*
20:33:17.371 [main] INFO com.kmy.study.operators.OperEx15_concat_02 -- #onNext :: [AstraZeneca,3000000]
20:33:17.374 [main] INFO com.kmy.study.operators.OperEx15_concat_02 -- #onNext :: [Janssen,2000000]
20:33:17.374 [main] INFO com.kmy.study.operators.OperEx15_concat_02 -- #onNext :: [Pfizer,1000000]
20:33:17.374 [main] INFO com.kmy.study.operators.OperEx15_concat_02 -- #onNext :: [Moderna,4000000]
20:33:17.374 [main] INFO com.kmy.study.operators.OperEx15_concat_02 -- #onNext :: [Novavax,2500000]
 */

}
