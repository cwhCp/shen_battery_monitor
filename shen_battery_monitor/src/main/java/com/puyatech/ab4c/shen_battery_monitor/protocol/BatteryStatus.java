package com.puyatech.ab4c.shen_battery_monitor.protocol;

import java.util.List;

public class BatteryStatus {

	private final List<StatusField> fields;

	public BatteryStatus(List<StatusField> fields) {
		this.fields = fields;
	}

	public List<StatusField> getFields() {
		return fields;
	}

}
