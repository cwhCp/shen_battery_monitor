package com.puyatech.ab4c.shen_battery_monitor;

import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.gui.StringRes;
import com.puyatech.ab4c.shen_battery_monitor.gui.StringResLoader;

public class MonitorHolder {

	private StringRes m_string_res;
	private final ComPortAgent m_com_agent;
	private Monitor m_monitor;

	public MonitorHolder(ComPortAgent agent) {
		this.m_com_agent = agent;
		this.m_monitor = new Monitor(agent);
	}

	public Monitor getMonitor() {
		return this.m_monitor;
	}

	public ComPortAgent getComPortAgent() {
		return this.m_com_agent;
	}

	public void loadStringRes(String name) {
		this.m_string_res = StringResLoader.load(name);
	}

	public StringRes getStringRes() {
		return this.m_string_res;
	}

}
