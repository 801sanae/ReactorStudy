package com.kmy;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        Flux<String> seq = Flux.just("Hello", "world");
        seq.map(str -> str.toUpperCase())
           .subscribe(data->System.out.println(data));

        /*
         * just는 (Mono) 0 or 1 데이터를 emit하는 Publisher
         */
        Mono.just("Hello world").subscribe(System.out::println);

        /*
         * empty는 데이터를 한 건도 emit하지 않는다. -> onCompleted signal 전송
         *
         * 작업이 끝났음을 알리고 이에 따른 후처리를 하고 싶을때 사용~
         */

        Mono.empty()
                .subscribe(
                        none -> System.out.println("emitted onNext signal"), // consumer – the consumer to invoke on each value
                        error-> {}, // errorConsumer – the consumer to invoke on error signal
                        ()-> System.out.println("emitted onComplete signal") // completeConsumer – the consumer to invoke on complete signal
                );
    }
}