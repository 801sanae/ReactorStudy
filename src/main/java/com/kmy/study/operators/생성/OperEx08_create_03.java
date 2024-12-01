package com.kmy.study.operators.생성;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

/**
 *  Flux.create()
 *  한번에 여러건의 데이터를 비동기적으로 emit 할땐, backpressure 전략 필수,
 */
@Slf4j
public class OperEx08_create_03 {

    static int start =1;
    static int end =4;


    public static void main(String[] args) throws InterruptedException{
        Flux
                .create((FluxSink<Integer> sink)->{
                        sink.onRequest(n->{
                            log.info("# requested : " + n);
                            try{
                                Thread.sleep(500L);
                                for(int i = start; i<=end; i++){
                                    sink.next(i);
                                }
                                start +=4;
                                end +=4;
                            }catch (InterruptedException e) {}
                        });

                        sink.onDispose(()->{
                            log.info("# clean up ");
                        });
                }, FluxSink.OverflowStrategy.DROP)  // Backpressure , DROP 전략
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel(), 2 )
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }
}
