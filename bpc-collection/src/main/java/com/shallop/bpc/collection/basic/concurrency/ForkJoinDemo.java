package com.shallop.bpc.collection.basic.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import static com.shallop.bpc.collection.utils.Printer.pt;

/**
 * @author chenxl
 * @date 2019/2/8
 */
public class ForkJoinDemo {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ForkJoinExample example = new ForkJoinExample(1, 10000);
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Future result = forkJoinPool.submit(example);
		System.out.println(result.get());
	}

}

class ForkJoinExample extends RecursiveTask<Integer> {

	private final int threshold = 5;
	private int first;
	private int last;

	public ForkJoinExample(int first, int last) {
		this.first = first;
		this.last = last;
	}

	@Override
	protected Integer compute() {
		int result = 0;
		if (last - first <= threshold) {
			// 任务足够小则直接计算
			for (int i = first; i <= last; i++) {
				result += i;
			}
			pt(Thread.currentThread().getName() + " computer:" + first + "::" + last);
		} else {
			// 拆分成小任务
			int middle = first + (last - first) / 2;
			ForkJoinExample leftTask = new ForkJoinExample(first, middle);
			ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
			leftTask.fork();
			rightTask.fork();
			result = leftTask.join() + rightTask.join();
			pt(Thread.currentThread().getName() + " fork join:" + first + "::" + middle + "::" + last);
		}
		return result;
	}
}
