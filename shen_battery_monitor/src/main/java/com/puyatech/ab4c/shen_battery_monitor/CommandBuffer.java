package com.puyatech.ab4c.shen_battery_monitor;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandBuffer {

	private Queue<String> list = new LinkedBlockingQueue<String>();

	public void push(String string) {
		string = this.io(string);
		if (string != null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String pop() {
		return this.io(null);
	}

	private synchronized String io(String string) {
		if (string == null) {
			return list.poll();
		} else {
			if (list.size() < 100) {
				list.add(string);
				return null;
			} else {
				return string;
			}
		}
	}

}
