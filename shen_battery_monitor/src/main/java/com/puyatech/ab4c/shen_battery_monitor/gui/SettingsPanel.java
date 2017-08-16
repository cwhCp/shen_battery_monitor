package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	public SettingCombo setcom_zdcx;
	public SettingCombo setcom_qygd;
	public SettingCombo setcom_hfjt;
	public SettingCombo setcom_bcdl;

	public JButton button_sdcx;
	public JButton button_hfqs;
	public JButton button_gdfz;
	public JButton button_jtfz;

	public SettingsPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static SettingsPanel create(MonitorHolder holder) {
		SettingsPanel view = new SettingsPanel(holder);
		view.onCreate();
		return view;
	}

	private JButton createButton(JPanel panel){
		JButton button = new JButton("button");
		panel.add(button);
		return button;
	}

	private SettingCombo createSettingCombo(){
		SettingCombo combo = SettingCombo.create();
		this.add(combo);
		// combo.setValue("2");
		return combo;
	}

	private void onCreate() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(2, 2);
		gl.setVgap(10);
		gl.setHgap(10);
		panel.setLayout(gl);
		this.add(panel);

		this.button_sdcx = this.createButton(panel);
		this.button_hfqs = this.createButton(panel);
		this.button_gdfz = this.createButton(panel);
		this.button_jtfz = this.createButton(panel);

		this.setcom_zdcx = this.createSettingCombo();
		this.setcom_qygd = this.createSettingCombo();
		this.setcom_hfjt = this.createSettingCombo();
		this.setcom_bcdl = this.createSettingCombo();

	}
}
