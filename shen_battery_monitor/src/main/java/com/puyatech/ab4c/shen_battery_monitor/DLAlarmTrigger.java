package com.puyatech.ab4c.shen_battery_monitor;

import java.util.Map;

import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;

public class DLAlarmTrigger implements AlarmTrigger {

	@Override
	public boolean hit(Map<String, StatusField> table) {
		// TODO Auto-generated method stub

		double vol = AlarmTrigger.Tools.value(table, "VOL");
		double clo = AlarmTrigger.Tools.value(table, "CLO");
		if (vol <= (clo + 0.2))
			return true;
		else
			return false;
	}

}
