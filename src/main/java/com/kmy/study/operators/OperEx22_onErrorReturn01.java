package com.kmy.study.operators;

import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * onErrorReturn
 * 대체값을 리턴한다.
 */
@Slf4j
public class OperEx22_onErrorReturn01 {

    public static void main(String[] args) {
        getBooks()
                .map(book -> book.getPenName().toUpperCase())
                .onErrorReturn("no pen name")
                .subscribe(log::info);
    }

    private static Flux<Book> getBooks() {
        return Flux.fromIterable(SampleData.books);
    }
}
