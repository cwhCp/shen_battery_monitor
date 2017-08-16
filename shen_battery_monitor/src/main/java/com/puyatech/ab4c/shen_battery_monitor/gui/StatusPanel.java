package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	public PropertyCombo combo_id;
	public PropertyCombo combo_dqdy;
	public PropertyCombo combo_dqdl;
	public PropertyCombo combo_dcwd;
	public PropertyCombo combo_xpwd;
	public PropertyCombo combo_fztd;
	public PropertyCombo combo_qygd;
	public PropertyCombo combo_hfjt;
	public PropertyCombo combo_zgl;
	public PropertyCombo combo_zdz;
	public PropertyCombo combo_dcdl;
	public PropertyCombo combo_dlbfb;

	public StatusPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static StatusPanel create(MonitorHolder holder) {
		StatusPanel view = new StatusPanel(holder);
		view.onCreate();
		return view;
	}

	private PropertyCombo createPropertyCombo(){
		PropertyCombo combo = PropertyCombo.create();
		combo.setValue("-");
		this.add(combo);
		return combo;
	}

	private void onCreate() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.combo_id = this.createPropertyCombo();
		this.combo_dqdy = this.createPropertyCombo();
		this.combo_dqdl = this.createPropertyCombo();
		this.combo_dcwd = this.createPropertyCombo();
		this.combo_xpwd = this.createPropertyCombo();
		this.combo_fztd = this.createPropertyCombo();
		this.combo_qygd = this.createPropertyCombo();
		this.combo_hfjt = this.createPropertyCombo();
		this.combo_zgl = this.createPropertyCombo();
		this.combo_zdz = this.createPropertyCombo();
		this.combo_dcdl = this.createPropertyCombo();
		this.combo_dlbfb = this.createPropertyCombo();
	}
}
