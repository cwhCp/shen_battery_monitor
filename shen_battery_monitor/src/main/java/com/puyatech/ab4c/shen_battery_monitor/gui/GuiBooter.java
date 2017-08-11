package com.puyatech.ab4c.shen_battery_monitor.gui;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class GuiBooter {

	private final MonitorHolder m_monitor;

	public GuiBooter(MonitorHolder monitor) {
		this.m_monitor = monitor;
	}

	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MonitorHolder monitor = GuiBooter.this.m_monitor;
				MainFrame mf = new MainFrame(monitor);
				mf.onCreate();
				mf.setVisible(true);
			}
		});

	}

}
