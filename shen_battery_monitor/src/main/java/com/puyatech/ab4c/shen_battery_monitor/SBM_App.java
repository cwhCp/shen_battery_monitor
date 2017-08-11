package com.puyatech.ab4c.shen_battery_monitor;

import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.com.GnuIoComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.com.TestingComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.gui.GuiBooter;

/**
 * Hello world!
 *
 */
public class SBM_App {

	public static void main(String[] args) {

		ComPortAgent agent = null;
		if (args != null) {
			for (String s : args) {
				if (s.equalsIgnoreCase("virtual")) {
					agent = new TestingComPortAgent();
					break;
				}
			}
		}
		if (agent == null) {
			agent = new GnuIoComPortAgent();
		}

		MonitorHolder holder = new MonitorHolder(agent);
		holder.loadStringRes("zh_cn.js");
		GuiBooter booter = new GuiBooter(holder);
		booter.start();

	}

}
