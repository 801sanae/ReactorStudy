package com.kmy.study.sequence;

import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ColdSequenceExample01 {

    public static void main(String[] args) throws InterruptedException{
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("KMY1", "KMY2", "KMY3")).map(String::toLowerCase);

        coldFlux.subscribe(name -> System.out.println(name));

        System.out.println("===============================================================");
        System.out.println("Cold Seqeunce는 Subcrible 시점이 달라도 데이터소스 처음부터 새롭게 전달받는다.");
        System.out.println("===============================================================");
        Thread.sleep(2000L);

        coldFlux.subscribe(name -> System.out.println(name));
    }
}
