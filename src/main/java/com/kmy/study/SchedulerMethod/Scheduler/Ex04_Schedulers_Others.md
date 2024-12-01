## Schedulers.boundedElastic()

- ExcutorService 기반 ThreadPool 생성하여 그 안에서 정해진만큼 Thread를 사용하여 작업처리 후 작업 종료 후 Thraed를 반납하여 재사용.

1. CPU 코어 x 10 Thread 생성.
2. Pool 내에 모든 Thread가 작업 처리 중일때 최대 100,000개의 작업이 큐에서 대기 가능.
3. Blocking I/O 작업에 효과적.
4. 실행 시간이 긴 Blocking I/O 작업이 포함되면 다른 Non-Blocking 처리에 영향을 주지 않도록 전용 스레드를 할당하여 작업처리에 유용함.

<br>

## Schedulers.parallel()

1. Non-Blocking I/O 작업에 최적화.
2. CPU 코어 수 만큼 스레드 생성.

<br>

## Schedulers.fromExecutorService()

1. 기존 이미 사용중인 ExcutorService로 부터 Scheduler를 생성하는 방식.
2. 비권장이라고 함.

<br>

## Scheduler.newXXXX()

- newSingle, newParallel, newBoundedElastic 등으로 새로운 instances를 생성하여 설정 가능하며, custom 하게 지정하여 사용할 수 있다.