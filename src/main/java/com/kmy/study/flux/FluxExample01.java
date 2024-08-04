package com.kmy.study.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/*
 * Flux는 데이터 0개 또는 1개 이상 emit할 수 있는 Reactor의 Publisher 타입
 */
public class FluxExample01 {
    public static void main(String[] args) {

        //#1 단순 데이터소스
        Flux.just(6,9,10)
                .map(n -> n%2)
                .subscribe(System.out::println);

        //#2 Array 데이터소스
        Flux.fromArray(new Integer[]{3,6,7,9})
                .filter(num -> num > 6)
                .map(num -> num *2)
                .subscribe(System.out::println);

        //#3
        // - justOnEmpty -> null 허용!
        // - concatWith -> 데이터소스로 연결 Mono와 Mono를 concat하여 하나의 flux로
        //
        Mono.justOrEmpty("KMY")
                .concatWith(Mono.justOrEmpty("KTE"))
                .subscribe(System.out::println);

        //#4
        // - collectList Flux<String> 데이터를 list 새로운 데이터 소스로 만든다.
        Flux
                .concat(Flux.just("kmy1","kmy1","kmy1"),
                        Flux.just("kmy2","kmy2","kmy2"),
                        Flux.just("kmy3","kmy3","kmy3"))
                .collectList()
                .subscribe(System.out::println);

    }
}
