package com.kmy.study.operators;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * collectMap
 * Upstream/Flux에서 emit한 데이터를 key/value로 변환하여 Map의 element에 add 후 map을 downstream에 emit한다.
 */
@Slf4j
public class OperEx19_collectMap {

    public static void main(String[] args) {
        Flux
                .range(0,26)
                .collectMap(key-> SampleData.morseCodes[key]
                          , value -> transformLetter(value))
                .subscribe(map -> log.info("{}", map));
    }

    private static String transformLetter(int value) {
        return Character.toString((char) ('a' + value));
    }


}
