package com.kmy;

import reactor.core.publisher.Flux;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Flux<String> seq = Flux.just("Hello", "world");
        seq.map(str -> str.toUpperCase())
           .subscribe(data->System.out.println(data));
    }
}