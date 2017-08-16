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

	public SettingCombo wnd_setcom_zdcx;
	public SettingCombo wnd_setcom_qygd;
	public SettingCombo wnd_setcom_hfjt;
	public SettingCombo wnd_setcom_bcdl;

	public JButton wnd_button_sdcx;
	public JButton wnd_button_hfqs;
	public JButton wnd_button_gdfz;
	public JButton wnd_button_jtfz;

	public SettingsPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static SettingsPanel create(MonitorHolder holder) {
		SettingsPanel view = new SettingsPanel(holder);
		view.onCreate();
		return view;
	}

	private void onCreate() {
		// this.setBackground(Color.red);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		List<JButton> buttons = new ArrayList<JButton>();
		for (int i = 4; i > 0; i--) {
			buttons.add(new JButton("button"));
		}
		this.add(this.layout(buttons));

		int i = 0;
		this.wnd_button_sdcx = buttons.get(i++);
		this.wnd_button_hfqs = buttons.get(i++);
		this.wnd_button_gdfz = buttons.get(i++);
		this.wnd_button_jtfz = buttons.get(i++);

		List<SettingCombo> list = new ArrayList<SettingCombo>();
		for (i = 4; i > 0; i--) {
			SettingCombo sub = SettingCombo.create();
			list.add(sub);
			this.add(sub);
			sub.setValue("2");
		}

		i = 0;
		this.wnd_setcom_zdcx = list.get(i++);
		this.wnd_setcom_qygd = list.get(i++);
		this.wnd_setcom_hfjt = list.get(i++);
		this.wnd_setcom_bcdl = list.get(i++);

	}

	private Component layout(List<JButton> buttons) {
		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(2, 2);
		gl.setVgap(10);
		gl.setHgap(10);
		panel.setLayout(gl);
		for (JButton btn : buttons) {
			panel.add(btn);
		}
		return panel;
	}
}
