package com.puyatech.ab4c.shen_battery_monitor;

public class CommandLooper {

	private final String command;
	private long delay;
	private long interval;
	private long nextTriggerTime;

	public CommandLooper(String cmd) {
		this.command = cmd;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getNextTriggerTime() {
		return nextTriggerTime;
	}

	public void setNextTriggerTime(long nextTriggerTime) {
		this.nextTriggerTime = nextTriggerTime;
	}

	public String getCommand() {
		return command;
	}

}
