package com.kmy.study.operators;

import com.kmy.study.sample.CryptoCurrencyPriceEmitter;
import com.kmy.study.sample.CryptoCurrencyPriceListener;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

/**
 * Flux.create()
 * #2 요청과 상관없이 비동기적 데이터 emit, push방식
 * 비동기적
 * 한번에 여러 건의 데이터 emitt
 * 프로그래밍적으로 signal Controll
 *
 */
@Slf4j
public class OperEx08_create_02 {
    public static void main(String[] args) throws InterruptedException{

        CryptoCurrencyPriceEmitter priceEmitter = new CryptoCurrencyPriceEmitter();

        Flux
                .create((FluxSink<Integer> sink)-> priceEmitter.setListener(new CryptoCurrencyPriceListener() {
                            @Override
                            public void onPrice(List<Integer> priceList) {
                                priceList.stream().forEach(price -> sink.next(price));
                            }

                            @Override
                            public void onComplete() {
                                sink.complete();
                            }
                        }))
                .publishOn(Schedulers.parallel())
                .subscribe(
                        data -> log.info("# onNext :: {}", data),
                        error -> {},
                        () -> log.info("# onComplete")
                );

        Thread.sleep(3000L);

        priceEmitter.flowInto();

        Thread.sleep(3000L);

        priceEmitter.complete();

//        01:03:29.963 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # start
//        01:03:31.043 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 1
//        01:03:31.047 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 2
//        01:03:32.050 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 3
//        01:03:32.050 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 4
//        01:03:33.051 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 5
//        01:03:33.051 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 6
//        01:03:34.056 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 7
//        01:03:34.056 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 8
//        01:03:35.061 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 9
//        01:03:35.061 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onNext: 10
//        01:03:36.063 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # onComplete!
//        01:03:36.064 [main] INFO com.kmy.study.operators.OperEx08_create_01 -- # clean up!

    }
}
