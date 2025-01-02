package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * window
 *
 * Upstream에서 emit되는 첫번째 데이터부터 maxSize 숫자만큼의 데이터를 포함하는 새로운 Flux로 분할
 *
 */
@Slf4j
public class OperEx27_window01 {

    public static void main(String[] args) {
        Flux.range(1,11)
                .window(3) //  4?
                .flatMap(flux -> {
                    log.info("==================");
                    return flux;
                })
                .subscribe(new BaseSubscriber<>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
//                        super.hookOnSubscribe(subscription);
                        subscription.request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
//                        super.hookOnNext(value);
                        log.info(" onNext : {}", value);
                        request(2);
                    }
                });
    }
}

/*
Flux.range(1, 11) 인데 window(3)이니 4 flux로 다운스트림에 emit
00:08:11.848 [main] INFO com.kmy.study.operators.OperEx27_window02 -- ==================
00:08:11.853 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 1
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 2
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 3
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 -- ==================
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 4
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 5
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 6
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 -- ==================
00:08:11.855 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 7
00:08:11.856 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 8
00:08:11.856 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 9
00:08:11.856 [main] INFO com.kmy.study.operators.OperEx27_window02 -- ==================
00:08:11.856 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 10
00:08:11.856 [main] INFO com.kmy.study.operators.OperEx27_window02 --  onNext : 11
 */