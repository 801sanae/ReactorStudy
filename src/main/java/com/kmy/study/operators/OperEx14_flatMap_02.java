package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * flatMap( )
 * upstream -> downstream
 * 구구단
 */
@Slf4j
public class OperEx14_flatMap_02 {

    public static void main(String[] args) throws InterruptedException{
        Flux
                .range(2,8)
                .flatMap( dan -> Flux
                                    .range(1,9)
                                    .map( n -> dan + " * " + n + " = "+dan*n))
                // upstream * inner sequence 만큼 emit
                .subscribe(log::info);

        Thread.sleep(100L);
    }
}
