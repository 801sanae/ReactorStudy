package com.kmy.study.operators.stream처리;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * flatMap( )
 * upstream -> downstream 에서
 * emit한 데이터를 변환하여 emit한다.
 */
@Slf4j
public class OperEx14_flatMap_01 {

    public static void main(String[] args) {
        Flux
                .just("good", "Bad") // upStream
                .flatMap( feeling -> Flux
                                        .just("Morning", "Afternoon", "Evening") // inner sequence
                                        .map( time -> feeling + " " + time))
                // upstream * inner sequence 만큼 emit
                .subscribe(log::info);
    }
}
