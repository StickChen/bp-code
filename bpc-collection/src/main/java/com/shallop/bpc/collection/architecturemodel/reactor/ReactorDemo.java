package com.shallop.bpc.collection.architecturemodel.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * @author chenxuanlong
 * @date 2018/1/20
 */
public class ReactorDemo {

	@Test
	public void testFlux(){
		Flux.just("Hello", "World").subscribe(System.out::println);
		Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
		Flux.empty().subscribe(System.out::println);
		Flux.range(1, 10).subscribe(System.out::println);
		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
		Flux.intervalMillis(1000).subscribe(System.out::println);
	}

	@Test
	public void testFluxGenerate() {
		Flux.generate(sink -> {
			sink.next("Hello");
			sink.complete();
		}).subscribe(System.out::println);

		final Random random = new Random();
		Flux.generate(ArrayList::new, (list, sink) -> {
			int value = random.nextInt(100);
			list.add(value);
			sink.next(value);
			if (list.size() == 10) {
				sink.complete();
			}
			return list;
		}).subscribe(System.out::println);

		Flux.create(sink -> {
			for (int i = 0; i < 10; i++) {
				sink.next(i);
			}
			sink.complete();
		}).subscribe(System.out::println);
	}

	@Test
	public void testMono(){
		Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
		Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
		Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
	}

	@Test
	public void testBuffer() {
		Flux.range(1, 100).buffer(20).subscribe(System.out::println);
		Flux.intervalMillis(100).bufferMillis(1001).take(2).toStream().forEach(System.out::println);
		Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
		Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);

		Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

		Flux.range(1, 100).window(20).subscribe(System.out::println);
		Flux.intervalMillis(100).windowMillis(1001).take(2).toStream().forEach(System.out::println);

		// zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并
		Flux.just("a", "b").zipWith(Flux.just("c", "d")).subscribe(System.out::println);
		Flux.just("a", "b").zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
				.subscribe(System.out::println);
	}

	@Test
	public void testTake(){
		// take 系列操作符用来从当前流中提取元素
		Flux.range(1, 1000).take(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
		Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);

		// reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
		Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
		Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);

		// merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。
		// 不同之处在于 merge 按照所有流中元素的实际产生顺序来合并，而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
		Flux.merge(Flux.intervalMillis(0, 100).take(5), Flux.intervalMillis(50, 100).take(5))
			.toStream()
			.forEach(System.out::println);
		Flux.mergeSequential(Flux.intervalMillis(0, 100).take(5), Flux.intervalMillis(50, 100).take(5))
			.toStream()
			.forEach(System.out::println);
		
		// flatMap 和 flatMapSequential 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并。
		Flux.just(5, 10)
			.flatMap(x -> Flux.intervalMillis(x * 10, 100).take(x))
			.toStream()
			.forEach(System.out::println);

		// concatMap 操作符的作用也是把流中的每个元素转换成一个流，再把所有流进行合并。
		Flux.just(5, 10)
			.concatMap(x -> Flux.intervalMillis(x * 10, 100).take(x))
			.toStream()
			.forEach(System.out::println);

		// combineLatest 操作符把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素。
		Flux.combineLatest(
				Arrays::toString,
				Flux.intervalMillis(100).take(5),
				Flux.intervalMillis(50, 100).take(5)
		).toStream().forEach(System.out::println);
		
	}
	
	@Test
	public void testSubscribe(){
		Flux.just(1, 2)
				.concatWith(Mono.error(new IllegalStateException()))
				.subscribe(System.out::println, System.err::println);

		// 出现错误时返回默认值
		Flux.just(1, 2)
			.concatWith(Mono.error(new IllegalStateException()))
			.onErrorReturn(0)
			.subscribe(System.out::println);

		// 出现错误时使用另外的流
		Flux.just(1, 2)
			.concatWith(Mono.error(new IllegalStateException()))
			.switchOnError(Mono.just(0))
			.subscribe(System.out::println);
		
		// 出现错误时根据异常类型来选择流
		Flux.just(1, 2).concatWith(Mono.error(new IllegalArgumentException())).onErrorResumeWith(e -> {
			if (e instanceof IllegalStateException) {
				return Mono.just(0);
			} else if (e instanceof IllegalArgumentException) {
				return Mono.just(-1);
			}
			return Mono.empty();
		}).subscribe(System.out::println);

		// 使用 retry 操作符进行重试
		Flux.just(1, 2)
			.concatWith(Mono.error(new IllegalStateException()))
			.retry(1)
			.subscribe(System.out::println);


	}

	@Test
	public void testScheduler(){
		// 通过调度器（Scheduler）可以指定这些操作执行的方式和所在的线程
		// publishOn()方法切换的是操作符的执行方式，而 subscribeOn()方法切换的是产生流中元素时的执行方式。
		Flux.create(sink -> {
					sink.next(Thread.currentThread().getName());
					sink.complete();
				})
			.publishOn(Schedulers.single())
			.map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
			.publishOn(Schedulers.elastic())
			.map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
			.subscribeOn(Schedulers.parallel())
			.toStream()
			.forEach(System.out::println);

	}

	@Test
	public void testTest(){
		// StepVerifier 的作用是可以对序列中包含的元素进行逐一验证。
		StepVerifier.create(Flux.just("a", "b"))
					.expectNext("a")
					.expectNext("b")
					.verifyComplete();

		// 操作测试时间
		StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
					.expectSubscription()
					.expectNoEvent(Duration.ofHours(4))
					.expectNext(0L)
					.thenAwait(Duration.ofDays(1))
					.expectNext(1L)
					.verifyComplete();

		// 使用 TestPublisher 创建测试所用的流
		final TestPublisher<String> testPublisher = TestPublisher.create();
		testPublisher.next("a");
		testPublisher.next("b");
		testPublisher.complete();

		StepVerifier.create(testPublisher)
					.expectNext("a")
					.expectNext("b")
					.expectComplete();


	}

	@Test
	public void testDebug(){
		// 启用调试模式
		Hooks.onOperator(providedHook -> providedHook.operatorStacktrace());

		// 使用 checkpoint 操作符
		Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);

	}

	@Test
	public void testLog(){
		// 使用 log 操作符记录事件
		Flux.range(1, 2).log("Range").subscribe(System.out::println);
	}

	@Test
	public void testDynamic() throws InterruptedException {
		// 热序列
		final Flux<Long> source = Flux.intervalMillis(1000)
										.take(10)
										.publish()
										.autoConnect();
		source.subscribe();
		Thread.sleep(5000);
		source.toStream().forEach(System.out::println);
	}



















}
