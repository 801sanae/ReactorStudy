package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Arrays;
import java.util.List;

/**
 * Flux.create()
 * #1 요청 개수 지정
 * 비동기적
 * 한번에 여러 건의 데이터 emitt
 * 프로그래밍적으로 signal Controll
 *
 */
@Slf4j
public class OperEx08_create_01 {

    static int SIZE = 0;
    static int COUNT = -1;
    final static List<Integer> DATA_SOURCE = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    public static void main(String[] args) {
        log.info("# start");

        Flux
                .create((FluxSink<Integer> sink)->{
                    sink.onRequest(n ->{
                        try{
                            Thread.sleep(1000L);

                            for(int i = 0; i< n; i++){
                                if(COUNT>=9) {
                                    sink.complete();
                                }else{
                                    COUNT++;
                                    sink.next(DATA_SOURCE.get(COUNT));
                                }
                            }

                        }catch (InterruptedException e){}
                    });
                    sink.onDispose(()->log.info("# clean up!")); // 후처리 로그 출력, FluxSink가 더이상 사용되지 않는다는 의미.
                }).subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription){
                        request(2); // 직접 요청 개수 지정하는 BaseSubscriber 사용
                        // 요청 개수만큼 데이터를 emit, pull방식으로 데이터 처리.
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
//                        super.hookOnNext(value);
                        SIZE++;
                        log.info("# onNext: {}", value);
                        if(SIZE ==2){
                            request(2);
                            SIZE=0;
                        }
                    }

                    @Override
                    protected void hookOnComplete() {
//                        super.hookOnComplete();
                        log.info("# onComplete!");
                    }
                });

//        01:03:29.963 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # start
//        01:03:31.043 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 1
//        01:03:31.047 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 2
//        01:03:32.050 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 3
//        01:03:32.050 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 4
//        01:03:33.051 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 5
//        01:03:33.051 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 6
//        01:03:34.056 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 7
//        01:03:34.056 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 8
//        01:03:35.061 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 9
//        01:03:35.061 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onNext: 10
//        01:03:36.063 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # onComplete!
//        01:03:36.064 [main] INFO com.kmy.study.operators.생성.OperEx08_create_01 -- # clean up!

    }
}
