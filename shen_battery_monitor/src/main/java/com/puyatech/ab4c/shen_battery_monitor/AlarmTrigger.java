package com.puyatech.ab4c.shen_battery_monitor;

import java.util.Map;

import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;

public interface AlarmTrigger {

	boolean hit(Map<String, StatusField> table);

	class Tools {

		public static double value(Map<String, StatusField> table, String key) {
			if (table == null) {
				return 0;
			}
			StatusField field = table.get(key);
			if (field == null) {
				return 0;
			}
			return field.getValue();
		}
	}

}
