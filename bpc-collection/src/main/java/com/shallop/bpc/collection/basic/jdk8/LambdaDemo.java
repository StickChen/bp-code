package com.shallop.bpc.collection.basic.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author chenxuanlong
 * @date 2017/4/6
 */
public class LambdaDemo {

	@Test
	public void lambda() {

		Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));

		Arrays.asList("a", "b", "d").sort((e1, e2) -> {
			int result = e1.compareTo(e2);
			return result;
		});

		Arrays.asList("a", "b", "d").sort(String::compareTo);
	}

	@Test
	public void testMethodRef() {
		final Car car = Car.create(Car::new);	// 构造器引用
		final List<Car> cars = Arrays.asList(car);
		cars.forEach(Car::collide);	// 静态方法引用
		cars.forEach(Car::repair);	// 特定类的任意对象的方法引用
	}

	@Test
	public void test23() {
		final Value<String> value = new Value<>();
		value.getOrDefault("22", Value.defaultValue());		// Value.defaultValue()的参数类型可以被推测出，所以就不必明确给出。
	}

	@Test
	public void testNull() {
		Optional<String> fullName = Optional.ofNullable(null);
		System.out.println("Full Name is set? " + fullName.isPresent());
		System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));
		System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));

		Optional<String> firstName = Optional.of("Tom");
		System.out.println("First Name is set? " + firstName.isPresent());
		System.out.println("First Name: " + firstName.orElseGet(() -> "[none]"));
		System.out.println(firstName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
		System.out.println();
	}

	public static void main(String args[]){
		LambdaDemo tester = new LambdaDemo();

		// 类型声明
		MathOperation addition = (int a, int b) -> a + b;

		// 不用类型声明
		MathOperation subtraction = (a, b) -> a - b;

		// 大括号中的返回语句
		MathOperation multiplication = (int a, int b) -> { return a * b; };

		// 没有大括号及返回语句
		MathOperation division = (int a, int b) -> a / b;

		System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
		System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
		System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
		System.out.println("10 / 5 = " + tester.operate(10, 5, division));

		// 不用括号
		GreetingService greetService1 = message ->
				System.out.println("Hello " + message);

		// 用括号
		GreetingService greetService2 = (message) ->
				System.out.println("Hello " + message);

		greetService1.sayMessage("Runoob");
		greetService2.sayMessage("Google");
	}

	interface MathOperation {
		int operation(int a, int b);
	}

	interface GreetingService {
		void sayMessage(String message);
	}

	private int operate(int a, int b, MathOperation mathOperation){
		return mathOperation.operation(a, b);
	}
}
