package com.shallop.bpc.collection.basic.concurrency;

/**
 * long变量读到无效值
 * @author chenxuanlong
 * @date 2016/10/10
 */
public class InvalidLongDemo2 {

	long count = 0;


	public static void demonstrate() {

		InvalidLongDemo2 demo = new InvalidLongDemo2();

		Thread thread = new Thread(demo.getConcurrencyCheckTask());
		thread.start();

		for (int i = 0; ; i++) {
			long l = i;
			demo.count = l << 32 | l;
		}

	}

	ConcurrencyCheckTask getConcurrencyCheckTask() {
		return new ConcurrencyCheckTask();
	}
	
	private class ConcurrencyCheckTask implements Runnable{

		@Override
		public void run() {
			int c = 0;
			for (int i = 0; ; i++) {
				long l = count;		// 赋值操作
				long high = l >>> 32;
				long low = l & 0xFFFFFFFFL;
				if (high != low) {
					c++;
					System.err.printf("Got invalid long!! check time=%s, happen time=%s(%s%%), count value=%s|%s\n", i + 1, c,
							(float) c / (i + 1) * 100, high, low);
					break;
				}else {
					System.out.printf("normal %s|%s\n", high, low);
				}
			}
		}
	}
}
