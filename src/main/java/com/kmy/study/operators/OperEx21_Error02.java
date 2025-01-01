package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.zip.DataFormatException;

/**
 * error
 * Flux.error
 * Mono.error
 */
@Slf4j
public class OperEx21_Error02 {

    public static void main(String[] args) {
        Flux.just('a', 'b', 'c', '3', 'd')
                .flatMap(letter -> {
                    try{
                        return convert(letter);
                    }catch (DataFormatException e){
                        return Flux.error(e);
                    }
                })
                .subscribe(data -> log.info(">>> {}", data)
                ,error -> log.error(">>> Error ::: \n", error));
    }

    public static Mono<String> convert(char c) throws DataFormatException{
        if(!Character.isAlphabetic(c)){
            throw new DataFormatException("Not Alphabetic");
        }
        return Mono.just("Convert to " + Character.toUpperCase(c));
    }
}
