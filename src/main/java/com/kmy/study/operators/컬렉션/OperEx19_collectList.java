package com.kmy.study.operators.컬렉션;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

/**
 * collectList
 * Upstream/Flux에서 emit한 데이터를 list로 변환하여 downstream에 emit한다.
 */
@Slf4j
public class OperEx19_collectList {

    public static void main(String[] args) {
        Flux
                .just("...","---","...")
                .map(code->transformMorseCode(code))
                .collectList()
                .subscribe(list -> log.info("{}", list.stream().collect(Collectors.joining())));
    }

    private static String transformMorseCode(String code) {
        return SampleData.morseCodeMap.get(code);
    }


}
