package com.kmy.study.flux;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * packageName    : com.kmy.study.flux
 * fileName       : FluxExample02_expendDeep
 * author         : kmy
 * date           : 3/4/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/25        kmy       최초 생성
 */
@Slf4j
public class FluxExample02_expendDeep {

    public static void main(String[] args) {

        // # Flux.expandDeep 또한 Mono.expandDeep과 동일하게 재귀호출한다.
        // # Flux.expandDeep를 이용한 재귀호출시 조건을 통한 재귀횟수 관리 필수인점은 공통점
        // # 요소에 대한 재귀호출은 동일하나, Flux는 요소마다의 재귀호출이 진행된다.

        Flux.just("kmy1","kmy2","kmy3").expandDeep(data -> {
            return data.length() > 50 ? Flux.empty() : Flux.just(data + " -> " + data);
        }).subscribe(data -> log.info("# onNext() : {}", data));


        // # Flux.expandDeep, Mono.expandDeep
        // # 아래의 결과는 동일한 결과를 emit하게 된다. ( return Flux )
        Flux.just("kmy1").expandDeep(data -> {
            return data.length() > 50 ? Flux.empty() : Flux.just(data + " -> " + data);
        }).subscribe(data -> log.info("# onNext() : {}", data));

        Mono.just("kmy1").expandDeep(data -> {
            return data.length() > 50 ? Flux.empty() : Flux.just(data + " -> " + data);
        }).subscribe(data -> log.info("# onNext() : {}", data));

    }
}
