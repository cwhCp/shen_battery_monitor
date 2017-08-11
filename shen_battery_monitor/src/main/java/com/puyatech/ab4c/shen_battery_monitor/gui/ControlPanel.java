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

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	public JButton wnd_button_on_off;
	public JComboBox<String> wnd_combo_port;
	public JLabel wnd_label_power;
	public JLabel wnd_label_com;
	public JLabel wnd_label_config;
	private ImageIcon[] m_icon_array;

	public ControlPanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static ControlPanel create(MonitorHolder holder) {
		ControlPanel view = new ControlPanel(holder);
		view.onCreate();
		return view;
	}

	private void onCreate() {

		// this.setBackground(Color.yellow);
		// this.setPreferredSize(new Dimension(100, 100));

		GridLayout layout = new GridLayout(1, 5);
		this.setLayout(layout);

		JLabel label1 = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();
		JButton button = new JButton();
		JComboBox<String> combo = new JComboBox<String>();

		label1.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label3.setHorizontalAlignment(JLabel.CENTER);

		label1.setText("label-1");
		label2.setText("label-2");
		button.setText("button");

		// label3.setText("label-3");

		ImageIcon icon0 = this.loadIcon(Color.red);
		ImageIcon icon1 = this.loadIcon(Color.green);
		ImageIcon[] icons = { icon0, icon1 };
		label3.setIcon(icon0);
		this.m_icon_array = icons;

		this.add(label1);
		this.add(combo);
		this.add(label2);
		this.add(button);
		this.add(label3);

		int lr = 50;
		int tb = 30;
		this.setBorder(BorderFactory.createEmptyBorder(tb, lr, tb, lr));

		this.wnd_button_on_off = button;
		this.wnd_combo_port = combo;
		this.wnd_label_com = label1;
		this.wnd_label_config = label2;
		this.wnd_label_power = label3;

	}

	public ImageIcon loadIcon(Color color) {
		String name = "red.png";
		if (color.equals(Color.green)) {
			name = "green.png";
		}
		URL url = this.getClass().getResource(name);
		return new ImageIcon(url);
	}

	public void setStateIcon(int index) {
		ImageIcon icon = this.m_icon_array[index];
		this.wnd_label_power.setIcon(icon);
	}

}
