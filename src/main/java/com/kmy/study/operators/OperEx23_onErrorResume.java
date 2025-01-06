package com.kmy.study.operators;

import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


/**
 * onErrorResume
 * 대체동작을 수행하도록한다.
 * 대체동작에서도 오류가 발생하면 onError Signal을 전송한다.
 */
@Slf4j
public class OperEx23_onErrorResume {

    public static void main(String[] args) {
        final String keyword = "DDD111111111";
//        final String keyword = "DDD";
        getBooksFromCache(keyword)
                .onErrorResume(error -> getBooksFromDatabase(keyword))
                .doOnError(error -> log.error("doOnError :", error))
                .subscribe(data -> log.info("# onNext ::{}", data.getBookName())
                , error -> log.error("onError {}"));

    }

    public static Flux<Book> getBooksFromCache(final String keyword){
        return Flux.fromIterable(SampleData.books)
                .filter(book -> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("no such book")));
    }

    public static Flux<Book> getBooksFromDatabase(final String keyword){
        List<Book> books = new ArrayList<>(SampleData.books);
        books.add(new Book("DDD: domain driven design", "joy", "ddd-main", 35000, 200));
        return Flux.fromIterable(books)
                .filter(book-> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("no such book")));
    }

    private static class NoSuchBookException extends RuntimeException{
        NoSuchBookException(String msg){
            super(msg);
        }
    }
}

/* 에러케이스일경우 doOnError, subscribe onError에 error Signal 전송된다.
> Task :OperEx23_onErrorResume.main()
15:42:38.035 [main] ERROR com.kmy.study.operators.OperEx23_onErrorResume -- doOnError :
com.kmy.study.operators.OperEx23_onErrorResume$NoSuchBookException: no such book
	at com.kmy.study.operators.OperEx23_onErrorResume.getBooksFromDatabase(OperEx23_onErrorResume.java:40)
	at com.kmy.study.operators.OperEx23_onErrorResume.lambda$main$0(OperEx23_onErrorResume.java:23)
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:94)
	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.onError(Operators.java:2210)
	at reactor.core.publisher.Operators.error(Operators.java:198)
	at reactor.core.publisher.FluxError.subscribe(FluxError.java:43)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8773)
	at reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onComplete(FluxSwitchIfEmpty.java:82)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onComplete(FluxFilterFuseable.java:171)
	at reactor.core.publisher.FluxIterable$IterableSubscriptionConditional.fastPath(FluxIterable.java:755)
	at reactor.core.publisher.FluxIterable$IterableSubscriptionConditional.request(FluxIterable.java:620)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:191)
	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.set(Operators.java:2341)
	at reactor.core.publisher.Operators$MultiSubscriptionSubscriber.onSubscribe(Operators.java:2215)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:87)
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:179)
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:83)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8773)
	at reactor.core.publisher.Flux.subscribeWith(Flux.java:8894)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8739)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8663)
	at reactor.core.publisher.Flux.subscribe(Flux.java:8633)
	at com.kmy.study.operators.OperEx23_onErrorResume.main(OperEx23_onErrorResume.java:25)
15:42:38.036 [main] ERROR com.kmy.study.operators.OperEx23_onErrorResume -- onError {}
 */