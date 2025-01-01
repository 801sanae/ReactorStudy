package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * onErrorContinue
 * error 발생시 error 영역내 데이터를 제거하고 upstream에서의 후속데이터를 emit한다.
 * 약간, 에러가 발생해도 다음 upstream데이터를 emit하여 진행하는 느낌임
 *
 * 대부분의 error처리를 doOnError, onErrorReturm으로 처리하도록 권장.
 */
@Slf4j
public class OperEx24_onErrorContinue {

    public static void main(String[] args) {
        Flux.just(1,2,3,4,57,8,0,6,12)
                .map(num -> 12 / num)
                .onErrorContinue((error, num) -> {
                    log.error("Error :: {} num :: {}", error.getMessage(), num);
                }).subscribe(data -> log.info("doNext :: {}", data),
                        error -> log.error("onError ", error));
    }
}
