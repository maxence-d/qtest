package wbyt.qtest2;

import static java.lang.System.currentTimeMillis;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {

	private static final Logger logger = Logger.getLogger("Default");

	public static void log(String s, Object... args) {
		logger.info(String.format(s, args));
	}

	public static void main(String[] args) throws InterruptedException {

		Thread thread;

		// Free increment
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				long start;
				long end;

				start = currentTimeMillis();
				freeIncrement();
				end = currentTimeMillis();
				log("freeIncrement: %d", end - start);
			}
		});
		thread.start();
		thread.join();

		// Increment with lock
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				long start;
				long end;
				start = currentTimeMillis();
				lockIncrement();
				end = currentTimeMillis();
				log("lockIncrement: %d", end - start);
			}
		});
		thread.start();
		thread.join();
		
		
		// Increment with cas
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				long start;
				long end;
				start = currentTimeMillis();
				casIncrement();
				end = currentTimeMillis();
				log("casIncrement: %d", end - start);
			}
		});
		thread.start();
		thread.join();

	}

	public static void freeIncrement() {
		long ctr = 0;
		for (long i = 0; i < 500000000; i++) {
			ctr++;
		}
		assert (ctr == 500000000);
	}

	public static void lockIncrement() {
		ReentrantLock lock = new ReentrantLock();
		long ctr = 0;
		for (long i = 0; i < 500000000; i++) {
			lock.lock();
			ctr++;
			lock.unlock();
		}
		assert (ctr == 500000000);
	}

	public static void casIncrement() {
		AtomicLong ctr = new AtomicLong();
		for (long i = 0; i < 500000000; i++) {
			long cur = ctr.longValue();
			ctr.compareAndSet(cur, cur + 1);
		}
		assert (ctr.longValue() == 500000000);
	}
}
