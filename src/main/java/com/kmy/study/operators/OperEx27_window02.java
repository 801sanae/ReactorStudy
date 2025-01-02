package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.math.MathFlux;

/**
 * window
 *
 * Upstream에서 emit되는 첫번째 데이터부터 maxSize 숫자만큼의 데이터를 포함하는 새로운 Flux로 분할
 * MathFlux 클래스내 sum average compare 등의 유틸성 클래스를 이용하여 window로 분할된 flux의 연산을 수행할 수 있다.
 */
@Slf4j
public class OperEx27_window02 {

    public static void main(String[] args) {
        Flux.fromIterable(SampleData.monthlyBookSales2021)
                .window(3)
                .flatMap(flux -> MathFlux.sumInt(flux)) // 3분할된 항목의 데이터를 Sum한다.
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
//                        super.hookOnSubscribe(subscription);
                        subscription.request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
//                        super.hookOnNext(value);
                        log.info("#onNext : {}", value);
                        request(2);
                    }
                });
    }
}
//> Task :OperEx27_window02.main()
//        00:24:44.425 [main] INFO com.kmy.study.operators.OperEx27_window02 -- #onNext : 8000000
//        00:24:44.428 [main] INFO com.kmy.study.operators.OperEx27_window02 -- #onNext : 16500000
//        00:24:44.429 [main] INFO com.kmy.study.operators.OperEx27_window02 -- #onNext : 7900000
//        00:24:44.429 [main] INFO com.kmy.study.operators.OperEx27_window02 -- #onNext : 14500000
