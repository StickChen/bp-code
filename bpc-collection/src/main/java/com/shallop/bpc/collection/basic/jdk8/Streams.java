package com.shallop.bpc.collection.basic.jdk8;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author chenxuanlong
 * @date 2017/4/7
 */
public class Streams {

	@Test
	public void testFlatMap(){
		List<Foo> foos = new ArrayList<>();

		// create foos
		IntStream
				.range(1, 4)
				.forEach(i -> foos.add(new Foo("Foo" + i)));

		// create bars
		foos.forEach(f ->
				IntStream
						.range(1, 4)
						.forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));

		foos.stream()
				.flatMap(f -> f.bars.stream())
				.forEach(b -> System.out.println(b.name));

		// Bar1 <- Foo1
		// Bar2 <- Foo1
		// Bar3 <- Foo1
		// Bar1 <- Foo2
		// Bar2 <- Foo2
		// Bar3 <- Foo2
		// Bar1 <- Foo3
		// Bar2 <- Foo3
		// Bar3 <- Foo3

		IntStream.range(1, 4)
				.mapToObj(i -> new Foo("Foo" + i))
				.peek(f -> IntStream.range(1, 4)
						.mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
						.forEach(f.bars::add))
				.flatMap(f -> f.bars.stream())
				.forEach(b -> System.out.println(b.name));


	}

	@Test
	public void collect(){

		// 构造我们自己所需的collector
		Collector<Person, StringJoiner, String> personNameCollector =
				Collector.of(
						() -> new StringJoiner(" | "),          // supplier
						(j, p) -> j.add(p.name.toUpperCase()),  // accumulator
						(j1, j2) -> j1.merge(j2),               // combiner
						StringJoiner::toString);                // finisher

		String names = persons
				.stream()
				.collect(personNameCollector);

		System.out.println(names);  // MAX | PETER | PAMELA | DAVID


		Map<Integer, String> map = persons
				.stream()
				.collect(Collectors.toMap(
						p -> p.age,
						p -> p.name,
						(name1, name2) -> name1 + ";" + name2));

		System.out.println(map);
		// {18=Max, 23=Peter;Pamela, 12=David}

		String phrase = persons
				.stream()
				.filter(p -> p.age >= 18)
				.map(p -> p.name)
				.collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

		System.out.println(phrase);
		// In Germany Max and Peter and Pamela are of legal age.

		IntSummaryStatistics ageSummary =
				persons
						.stream()
						.collect(Collectors.summarizingInt(p -> p.age));

		System.out.println(ageSummary);
		// IntSummaryStatistics{count=4, sum=76, min=12, average=19.000000, max=23}

		Double averageAge = persons
				.stream()
				.collect(Collectors.averagingInt(p -> p.age));

		System.out.println(averageAge);     // 19.0

	}

	class Person {
		String name;
		int age;

		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	List<Person> persons =
			Arrays.asList(
					new Person("Max", 18),
					new Person("Peter", 23),
					new Person("Pamela", 23),
					new Person("David", 12));


	@Test
	public void range(){
		IntStream.range(0, 10).forEach(System.out::println);
	}

	private enum Status {
		OPEN, CLOSED
	}

	private static final class Task {
		private final Status status;
		private final Integer points;

		Task(final Status status, final Integer points) {
			this.status = status;
			this.points = points;
		}

		public Integer getPoints() {
			return points;
		}

		public Status getStatus() {
			return status;
		}

		@Override
		public String toString() {
			return String.format("[%s, %d]", status, points);
		}
	}

	@Test
	public void testStreams() {
		final Collection<Task> tasks = Arrays.asList(new Task(Status.OPEN, 5), new Task(Status.OPEN, 13), new Task(Status.CLOSED,
				8));

		// Calculate total points of all active tasks using sum()
		final long totalPointsOfOpenTasks = tasks.stream().filter(task -> task.getStatus() == Status.OPEN)
				.mapToInt(Task::getPoints).sum();
		System.out.println("Total points: " + totalPointsOfOpenTasks);
		
		
	}
	
	@Test
	public void testStreams2() {
		final Collection<Task> tasks = Arrays.asList(new Task(Status.OPEN, 5), new Task(Status.OPEN, 13), new Task(Status.CLOSED,
				8));

		// Calculate total points of all tasks
		final double totalPoints = tasks.stream().parallel().map(task -> task.getPoints()) // or map( Task::getPoints )
				.reduce(0, Integer::sum);

		System.out.println("Total points (all tasks): " + totalPoints);

		// Group tasks by their status
		final Map<Status, List<Task>> map = tasks.stream().collect(Collectors.groupingBy(Task::getStatus));
		System.out.println(map);

		// Calculate the weight of each tasks (as percent of total points)
		final Collection<String> result = tasks.stream()                                        // Stream< String >
				.mapToInt(Task::getPoints)                     // IntStream
				.asLongStream()                                  // LongStream
				.mapToDouble(points -> points / totalPoints)   // DoubleStream
				.boxed()                                         // Stream< Double >
				.mapToLong(weight -> (long) (weight * 100)) // LongStream
				.mapToObj(percentage -> percentage + "%")      // Stream< String>
				.collect(Collectors.toList());                 // List< String >

		System.out.println(result);
	}
	
	@Test
	public void testFile() throws IOException {
		final Path path = new File("").toPath();
		try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
			lines.onClose(() -> System.out.println("Done!")).forEach(System.out::println);
		}
	}

	class Foo {
		String name;
		List<Bar> bars = new ArrayList<>();

		Foo(String name) {
			this.name = name;
		}
	}

	class Bar {
		String name;

		Bar(String name) {
			this.name = name;
		}
	}
}