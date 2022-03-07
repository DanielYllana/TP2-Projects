package launcher;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

class MainTest {

	static private boolean run(String inFile, String outFile, String expOutFile, Integer ticks) {

		try {
			simulator.launcher.Main.main(new String[] { "-i", inFile, "-o", outFile, "-t", ticks.toString() });

			File currRunOutFile = new File(outFile);
			File expectedOutFile = new File(expOutFile);
			
			JSONObject jo1 = new JSONObject(new JSONTokener(new FileInputStream(currRunOutFile)));
			JSONObject jo2 = new JSONObject(new JSONTokener(new FileInputStream(expectedOutFile)));



			return jo1.similar(jo2);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void test_1() {
		// error between 141 and 142 (v1 should move to r3, but it stays in r1)
		assertTrue(run("TrafficSimulatorTP/resources/examples/ex1.json", "TrafficSimulatorTP/resources/tmp/ex1.junit.out.json", "TrafficSimulatorTP/resources/examples/ex1.expout.json",
				300));

	}

	@Test
	void test_2() {
		assertTrue(run("TrafficSimulatorTP/resources/examples/ex2.json", "TrafficSimulatorTP/resources/tmp/ex2.junit.out.json", "TrafficSimulatorTP/resources/examples/ex2.expout.json",
				300));

	}

	@Test
	void test_3() {
		assertTrue(run("TrafficSimulatorTP/resources/examples/ex3.json", "TrafficSimulatorTP/resources/tmp/ex3.junit.out.json", "TrafficSimulatorTP/resources/examples/ex3.expout.json",
				150));

	}

}
