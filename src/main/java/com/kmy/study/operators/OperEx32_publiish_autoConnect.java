package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * autoConnect(n)
 * n만큼 subscrible이 발생할때 Upstream에 자동연결
 */
@Slf4j
public class OperEx32_publiish_autoConnect {

    public static void main(String[] args) throws InterruptedException{
        Flux<String> publisher = Flux.just("Concert part1", "Concert part3", "Concert part3")
                .delayElements(Duration.ofMillis(300L))
                .publish()
                .autoConnect(2);

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 1 is watching {}", data));

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 2 is watching {}", data));

        Thread.sleep(500L);
        publisher.subscribe(data -> log.info("#audience 3 is watching {}", data));

        Thread.sleep(1000L);
    }
}
/*
00:23:06.511 [parallel-1] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 1 is watching Concert part1
00:23:06.514 [parallel-1] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 2 is watching Concert part1
00:23:06.817 [parallel-2] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 1 is watching Concert part3
00:23:06.817 [parallel-2] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 2 is watching Concert part3
00:23:06.817 [parallel-2] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 3 is watching Concert part3
00:23:07.122 [parallel-3] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 1 is watching Concert part3
00:23:07.122 [parallel-3] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 2 is watching Concert part3
00:23:07.122 [parallel-3] INFO com.kmy.study.operators.OperEx32_publiish_autoConnect -- #audience 3 is watching Concert part3
 */