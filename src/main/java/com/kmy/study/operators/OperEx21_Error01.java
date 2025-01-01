package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * error
 * Flux.error
 * Mono.error
 */
@Slf4j
public class OperEx21_Error01 {

    public static void main(String[] args) {
        Flux.range(1,5)
                .flatMap(num -> {
                    if((num*2) % 3 == 0){
                        //1. DownStream에 emit을 허용하지 않기 위해 error를 리톤한다.
                        return Flux.error(new IllegalArgumentException("Not allowed Multiple of 3"));
                    }else{
                        return Mono.just(num * 2);
                    }
                })
                .subscribe(data -> log.info("onNext : {}", data)
                        //2. error 캐치
                         , error -> log.error("onError :: ", error)
                );
    }
}
