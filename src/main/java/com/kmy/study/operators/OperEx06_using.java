package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


/**
 * Flux.using()
 * 1. 파일을 읽는다.
 * 2. 파일의 라인데이터를 데이터소스로 전달(fromStream)
 * 3. emitt이 끝나면 Stream을 닫는다.
 */
@Slf4j
public class OperEx06_using {

    public static void main(String[] args) {

        Path path = Paths.get("");

        // 파일을 모두 읽을떄 까지 반복?
        Flux
                .using(()-> Files.lines(path), Flux::fromStream, Stream::close)
                .subscribe(log::info);
    }
}
