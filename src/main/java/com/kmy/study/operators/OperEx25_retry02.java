package com.kmy.study.operators;


import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Collectors;

@Slf4j
public class OperEx25_retry02 {
    public static void main(String[] args) throws InterruptedException{
        getBooks().collect(Collectors.toSet())
                .subscribe(bookSet -> bookSet.stream().forEach(book -> log.info("bookName : {}, price : {},", book.getBookName(), book.getPrice())));
        Thread.sleep(12000);
    }

    public static Flux<Book> getBooks(){
        final int[] count = {0};
        return Flux.fromIterable(SampleData.books)
                .delayElements(Duration.ofMillis(500))
                .map(book ->{
                    try{
                        count[0]++;
                        if(count[0] ==3){
                            Thread.sleep(2000);
                        }
                    }catch (InterruptedException e){}
                    return book;
                })
                .timeout(Duration.ofSeconds(2))
                .retry(2)
                .doOnNext(book -> log.info("# getBooks > doOnNext : {}, price", book.getBookName(), book.getPrice()));
    }
}
