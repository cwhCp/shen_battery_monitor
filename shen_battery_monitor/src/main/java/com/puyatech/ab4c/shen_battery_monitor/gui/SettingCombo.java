package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingCombo extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyLayout m_layout;

	public JButton wnd_button;

	private SettingCombo() {
	}

	public static SettingCombo create() {
		SettingCombo view = new SettingCombo();
		view.onCreate();
		return view;
	}

	private void onCreate() {

		MyLayout layout = new MyLayout();

		layout.label = new JLabel();
		layout.unit = new JLabel();
		layout.edit = new JTextField();
		layout.button = new JButton();

		layout.init(this);
		this.setLayout(layout);
		// this.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

		this.m_layout = layout;

		this.wnd_button = layout.button;
	}

	private class MyLayout implements LayoutManager {

		final Dimension dim = new Dimension(10, 10);

		public JLabel label;
		public JLabel unit;
		public JButton button;
		public JTextField edit;

		private MyLayout() {
		}

		public void init(SettingCombo parent) {
			parent.add(label);
			parent.add(unit);
			parent.add(edit);
			parent.add(button);

			unit.setText("unit");
			unit.setHorizontalAlignment(JLabel.CENTER);

			edit.setText("value");
			edit.setHorizontalAlignment(JTextField.CENTER);

			button.setText("button");

			label.setText("name");

		}

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void layoutContainer(Container parent) {

			Component com0 = this.label;
			Component com1 = this.edit;
			Component com2 = this.unit;
			Component com3 = this.button;

			final int w, h, h_2, w_a, w_b, w_c;
			w = parent.getWidth();
			h = parent.getHeight();
			h_2 = h / 2;
			w_b = h_2;
			w_a = w_c = (w - h_2) / 2;

			int top = 10;

			com0.setBounds(0, top, w, h_2 - top);
			int off = 0;
			com1.setBounds(off, h_2, w_a, h_2);
			off += w_a;
			com2.setBounds(off, h_2, w_b, h_2);
			off += w_b;
			com3.setBounds(off, h_2, w_c, h_2);

		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return dim;
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return dim;
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}
	}

	public void setTitle(String string) {
		this.m_layout.label.setText(string);
	}

	public void setUnit(String string) {
		this.m_layout.unit.setText(string);
	}

	public void setButtonText(String string) {
		this.m_layout.button.setText(string);
	}

	public void setValue(String string) {
		this.m_layout.edit.setText(string);
	}

	public String getValue() {
		return this.m_layout.edit.getText();
	}

}
