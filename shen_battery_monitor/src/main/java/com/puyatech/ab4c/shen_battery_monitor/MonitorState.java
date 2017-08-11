package com.puyatech.ab4c.shen_battery_monitor;

import java.util.HashMap;
import java.util.Map;

import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;

public class MonitorState {

	private long id;
	private Map<String, StatusField> fields;

	public MonitorState() {
	}

	public Map<String, StatusField> getFields() {
		return fields;
	}

	public void setFields(Map<String, StatusField> fields) {
		this.fields = fields;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MonitorState makeCopy() {
		Map<String, StatusField> f2 = new HashMap<String, StatusField>();
		f2.putAll(fields);
		MonitorState ms2 = new MonitorState();
		ms2.setId(id);
		ms2.setFields(f2);
		return ms2;
	}

}
