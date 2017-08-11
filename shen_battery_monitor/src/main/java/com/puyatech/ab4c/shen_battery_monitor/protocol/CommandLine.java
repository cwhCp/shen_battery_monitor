package com.puyatech.ab4c.shen_battery_monitor.protocol;

public class CommandLine {

	private String m_string;
	private String[] m_array;

	public CommandLine(String head, String[] param) {
		this.m_string = head;
		this.m_array = param;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.m_string);
		for (String s : m_array) {
			sb.append("\t").append(s);
		}
		return sb.toString();
	}
}
