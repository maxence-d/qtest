package wbyt.qtest2;

import static java.lang.System.currentTimeMillis;

import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {

	private static final Logger logger = Logger.getLogger("Default");

	public static void log(String s, Object... args) {
		logger.info(String.format(s, args));
	}

	public static void main(String[] args) {
		long start;
		long end;

		start = currentTimeMillis();
		freeIncrement();
		end = currentTimeMillis();
		log("freeIncrement: %d", end - start);
		

	}

	public static void freeIncrement() {
		// Example function logic
		long ctr = 0;
		for (long i = 0; i < 500000000; i++) {
			ctr++;
		}
		assert (ctr == 500000000);
	}
}
