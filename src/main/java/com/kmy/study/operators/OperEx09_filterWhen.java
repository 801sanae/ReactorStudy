package com.kmy.study.operators;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.Map;


/**
 *  Flux.filterWhen
 *  Inner Sequence를 통해 조건에 맞는 데이터 인지 비동기적으로 테스트 하고 true이면 upstream -> downstream data를 emit한다.
 */
@Slf4j
public class OperEx09_filterWhen {
    public static void main(String[] args) throws InterruptedException {
        Map<SampleData.CovidVaccine, Tuple2<SampleData.CovidVaccine, Integer>> covidVaccines = SampleData.getCovidVaccines();

        Flux
                .fromIterable(SampleData.coronaVaccineNames)
                .filterWhen(covidVaccine ->
                        Mono
                                .just(covidVaccines.get(covidVaccine).getT2()>=3_000_000)
                                .publishOn(Schedulers.parallel())
                )
                .subscribe(data->log.info("# {}" , data));
        Thread.sleep(1000);
    }

}
