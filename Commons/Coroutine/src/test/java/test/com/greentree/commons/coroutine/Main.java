package test.com.greentree.commons.coroutine;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import com.greentree.commons.coroutine.BaseCoroutineEnvironment;
import com.greentree.commons.coroutine.Sleep;
import com.greentree.commons.tests.ExecutionSequence;

public class Main {
	
	@Test
	void test1() throws InterruptedException, ExecutionException {
		var sequence = new ExecutionSequence();
		var env = new BaseCoroutineEnvironment();
		env.run(s -> {
			return switch(s) {
				case 0 -> {
					sequence.accept(0);
					yield new Sleep(5);
				}
				case 1 -> {
					sequence.accept(1);
					yield new Sleep(5);
				}
				default -> throw new NullPointerException();
			};
		}, 2);
	}
	
}
