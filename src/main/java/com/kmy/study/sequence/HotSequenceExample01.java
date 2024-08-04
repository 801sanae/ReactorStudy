package com.kmy.study.sequence;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotSequenceExample01 {
    public static void main(String[] args) throws InterruptedException {
        String[] singers = {"kmy1","kmy2","kmy3","kmy4","kmy5","kmy6","kmy7"};

        System.out.println("Begin!!!!!!");

        // # Hot Sequences
        //  - 원본 데이터 소스 Flux 를 공유한다.
        Flux<String> shared = Flux.fromArray(singers)
                .delayElements(Duration.ofSeconds(1))
                .share();

        shared.subscribe(singer -> System.out.println("subscrible1 -> " +  singer));

        Thread.sleep(2500);

        shared.subscribe(singer -> System.out.println("subscrible2 -> " +  singer));

        Thread.sleep(3000);

    }
}
