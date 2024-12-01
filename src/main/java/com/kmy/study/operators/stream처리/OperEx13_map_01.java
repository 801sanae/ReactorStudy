package com.kmy.study.operators.stream처리;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * map( n->n )
 * upstream -> downstream 에서
 * emit한 데이터를 변환하여 emit한다.
 */
@Slf4j
public class OperEx13_map_01 {

    public static void main(String[] args) {
        Flux
                .just("1-test","2-test","2-test")
                .map(d->d.replace("test", "txt"))
                .subscribe(f->log.info("# onNext : {}", f));

    }
}
