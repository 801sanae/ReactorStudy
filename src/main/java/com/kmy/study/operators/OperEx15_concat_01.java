package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * concat
 * publisher의 seq를 연결해서 순차적으로 emit해준다.
 * 먼저 입력된 publisher의 subscirble가 끝나면 "순차적"
 */
@Slf4j
public class OperEx15_concat_01 {
    public static void main(String[] args) {
        Flux
                .concat(Flux.just(1,2,3),Flux.just(4,5,6))
                .subscribe(d->log.info("#onNext :: {}", d));
    }
}
