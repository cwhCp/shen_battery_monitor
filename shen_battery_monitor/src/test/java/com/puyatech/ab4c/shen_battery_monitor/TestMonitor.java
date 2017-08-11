package com.puyatech.ab4c.shen_battery_monitor;

import org.junit.Test;

import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.com.TestingComPortAgent;

public class TestMonitor {

	@Test
	public void test() {

		ComPortAgent agent = new TestingComPortAgent();
		Monitor monitor = new Monitor(agent);
		monitor.start();

		this.sleep(3000);

		monitor.stop();

		this.sleep(2000);

	}

	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
