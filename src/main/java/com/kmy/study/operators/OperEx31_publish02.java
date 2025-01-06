package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * publish
 *
 * 다수의 subscribler에게 flux를 멀티캐스팅하는 operator
 * Cold Sequence를 Hot Sequence로 동작하게 해준다.
 * subscrilber가 구독하면 upStream에서 emit된 데이터가 구독중인 모든 subscribler에게 멀티캐스팅된다.
 */
@Slf4j
public class OperEx31_publish02 {

    private static ConnectableFlux<String> publisher;
    private static int checkedAudience;

    static {
            publisher = Flux.just("Concert part1", "Concert part3", "Concert part3")
                .delayElements(Duration.ofMillis(300L))
                .publish();
    }

    public static void main(String[] args) throws InterruptedException{
        checkAudience();
        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 1 is watching {}", data));
        checkedAudience++;

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 2 is watching {}", data));
        checkedAudience++;

        checkAudience();

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 3 is watching {}", data));

        Thread.sleep(1000L);
    }

    public static void checkAudience(){
        if(checkedAudience >=2) publisher.connect();
    }
}

/*
00:16:41.917 [parallel-1] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 1 is watching Concert part1
00:16:41.921 [parallel-1] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 2 is watching Concert part1
00:16:42.222 [parallel-2] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 1 is watching Concert part3
00:16:42.222 [parallel-2] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 2 is watching Concert part3
00:16:42.222 [parallel-2] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 3 is watching Concert part3
00:16:42.526 [parallel-3] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 1 is watching Concert part3
00:16:42.526 [parallel-3] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 2 is watching Concert part3
00:16:42.526 [parallel-3] INFO com.kmy.study.operators.OperEx31_publish02 -- #audience 3 is watching Concert part3
 */