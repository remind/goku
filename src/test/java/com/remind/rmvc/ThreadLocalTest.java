package com.remind.rmvc;

public class ThreadLocalTest {

	private Integer a;
	private static ThreadLocal<Integer> aHolder = new ThreadLocal<Integer>();
	
	public void init() {
		System.out.println("init:" + Thread.currentThread().getId());
		if (aHolder.get() == null) {
			System.out.println("aaaaaaaa");
			aHolder.set(1);
		}
		a = aHolder.get();
	}
	
	public void aa() {
		a += 1;
	}
	public Integer getA() {
		return a;
	}
	
	private static class RunThread extends Thread {
		private ThreadLocalTest tlt;
		public RunThread(ThreadLocalTest tlt) {
			this.tlt = tlt;
			tlt.init();
		}
		
		@Override
		public void run() {
			tlt.aa();
			System.out.println(Thread.currentThread().getId() + ":" + getName() + ":" + tlt.getA());
		}
	}
	
	public static void main(String[] args) {
		ThreadLocalTest tlt = new ThreadLocalTest();
		System.out.println("main:" + Thread.currentThread().getId());
		for(int i = 0; i < 10; i++) {
			new ThreadLocalTest.RunThread(tlt).start();
		}
	}
} 
