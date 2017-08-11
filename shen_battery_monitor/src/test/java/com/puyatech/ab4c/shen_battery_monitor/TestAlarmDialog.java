package com.puyatech.ab4c.shen_battery_monitor;

import org.junit.Test;

import com.puyatech.ab4c.shen_battery_monitor.gui.AlarmDialog;

public class TestAlarmDialog {

	@Test
	public void test() {

		AlarmDialog.Builder builder = new AlarmDialog.Builder();

		builder.setIcon(AlarmDialog.Icon.dl);
		builder.setSound(AlarmDialog.Sound.dl);
		builder.setMessage("dlbz");
		builder.create().show();

		builder.setIcon(AlarmDialog.Icon.qy);
		builder.setSound(AlarmDialog.Sound.qy);
		builder.setMessage("qygd");
		builder.create().show();

	}

}
