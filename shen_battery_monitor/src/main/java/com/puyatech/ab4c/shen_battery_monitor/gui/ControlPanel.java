package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ComboBoxModel;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	public JButton wnd_button_on_off;
	public JComboBox<String> wnd_combo_port;
	public JLabel wnd_label_power;
	public JLabel wnd_label_com;
	public JLabel wnd_label_config;

	public ControlPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static ControlPanel create(MonitorHolder holder) {
		ControlPanel view = new ControlPanel(holder);
		view.onCreate();
		return view;
	}

	private JLabel createCenterLabel(String text, String iconName = null){
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		if (null != iconName) {
			label.setIcon(new ImageIcon(this.getClass().getResource(iconName)));
		}
		return label;
	}

	private void onCreate() {
		this.setLayout(new GridLayout(1, 5));

		this.wnd_label_com = this.createCenterLabel("label-1");
		this.wnd_combo_port = new JComboBox<String>();
		this.wnd_label_config = this.createCenterLabel("label-2");
		this.wnd_button_on_off = new JButton();
		this.wnd_button_on_off.setText("button");
		this.wnd_label_power = this.createCenterLabel("","red.png");

		this.add(this.wnd_label_com);
		this.add(this.wnd_combo_port);
		this.add(this.wnd_label_config);
		this.add(this.wnd_button_on_off);
		this.add(this.wnd_label_power);

		int lr = 50;
		int tb = 30;
		this.setBorder(BorderFactory.createEmptyBorder(tb, lr, tb, lr));
	}

	public void setStateIcon(int index) {
		this.wnd_label_power.setIcon(new ImageIcon(this.getClass().getResource(index == 0? "red.png" :"green.png")));
	}

	public void setInfoByRes(StringRes res){
		wnd_label_com.setText(res.getComPort());
		wnd_label_config.setText("9600-8-N-1");
		wnd_label_power.setText("");
		wnd_button_on_off.setText(res.getOnOff());
	}

	public void setComboPort(ComboBoxModel<String> model){
		wnd_combo_port.setModel(model);
	}
}
