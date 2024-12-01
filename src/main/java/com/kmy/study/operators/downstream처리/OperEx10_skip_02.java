package com.kmy.study.operators.downstream처리;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * skip(n)
 * Upstream에서 emit 된 데이터 중 파라미터로 받은 숫자(시간 지정)만큼 건너뛰고 DownStream으로 emit한다.
 */
@Slf4j
public class OperEx10_skip_02 {
    public static void main(String[] args) throws InterruptedException{
        Flux
                .interval(Duration.ofMillis(300))
                .skip(Duration.ofSeconds(1))
                .subscribe(data->log.info("#onNext : {} ", data));

        Thread.sleep(2000L);
    }
}
