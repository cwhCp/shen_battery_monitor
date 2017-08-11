package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	public PropertyCombo wnd_combo_id;
	public PropertyCombo wnd_combo_dqdy;
	public PropertyCombo wnd_combo_dqdl;
	public PropertyCombo wnd_combo_dcwd;
	public PropertyCombo wnd_combo_xpwd;
	public PropertyCombo wnd_combo_fztd;
	public PropertyCombo wnd_combo_qygd;
	public PropertyCombo wnd_combo_hfjt;
	public PropertyCombo wnd_combo_zgl;
	public PropertyCombo wnd_combo_zdz;
	public PropertyCombo wnd_combo_dcdl;
	public PropertyCombo wnd_combo_dlbfb;

	public StatusPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static StatusPanel create(MonitorHolder holder) {
		StatusPanel view = new StatusPanel(holder);
		view.onCreate();
		return view;
	}

	private void onCreate() {

		// this.setBackground(Color.blue);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		List<PropertyCombo> list = new ArrayList<PropertyCombo>();
		for (int i = 12; i > 0; i--) {
			PropertyCombo sub = PropertyCombo.create();
			list.add(sub);
			this.add(sub);
			sub.setValue("-");
		}

		int i = 0;
		this.wnd_combo_id = list.get(i++);
		this.wnd_combo_dqdy = list.get(i++);
		this.wnd_combo_dqdl = list.get(i++);
		this.wnd_combo_dcwd = list.get(i++);
		this.wnd_combo_xpwd = list.get(i++);
		this.wnd_combo_fztd = list.get(i++);
		this.wnd_combo_qygd = list.get(i++);
		this.wnd_combo_hfjt = list.get(i++);
		this.wnd_combo_zgl = list.get(i++);
		this.wnd_combo_zdz = list.get(i++);
		this.wnd_combo_dcdl = list.get(i++);
		this.wnd_combo_dlbfb = list.get(i++);

	}

}
