package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PropertyCombo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel m_name;
	private JTextField m_value;

	private PropertyCombo() {
	}

	public static PropertyCombo create() {
		PropertyCombo view = new PropertyCombo();
		view.onCreate();
		return view;
	}

	private void onCreate() {
		JLabel text = new JLabel();
		JTextField edit = new JTextField();

		text.setHorizontalAlignment(JLabel.RIGHT);
		edit.setHorizontalAlignment(JTextField.CENTER);
		edit.setEditable(false);

		this.setLayout(new GridLayout(1, 2));
		this.add(text);
		this.add(edit);

		text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		this.setBorder(BorderUtils.getBorder(this));

		this.m_name = text;
		this.m_value = edit;

	}

	public void setName(String s) {
		this.m_name.setText(s);
	}

	public void setValue(String s) {
		this.m_value.setText(s);
	}
}
