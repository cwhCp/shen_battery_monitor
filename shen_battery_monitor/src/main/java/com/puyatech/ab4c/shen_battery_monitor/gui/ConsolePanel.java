package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class ConsolePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;
	private JTextArea m_text_view;

	public ConsolePanel(MonitorHolder holder) {
		this.m_holder = holder;
	}

	public static ConsolePanel create(MonitorHolder holder) {
		ConsolePanel view = new ConsolePanel(holder);
		view.onCreate();
		return view;
	}

	private void onCreate() {
		this.m_holder.getMonitor();
//		this.setBackground(Color.green);
		this.setPreferredSize(new Dimension(100, 100));

		JTextArea text = new JTextArea();
		JScrollPane sp = new JScrollPane();

		text.setText(this.sampleText());
		text.setEditable(false);

		this.setLayout(new BorderLayout());
		this.add(sp);
		sp.setViewportView(text);

		this.m_text_view = text;
	}

	public String sampleText() {
		final String nl = "\r\n";
		StringBuilder sb = new StringBuilder();
		sb.append(nl);
		return sb.toString();
	}

	public void append(String str) {
		int max_length = 1024 * 16;
		this.m_text_view.append(str);
		String text = this.m_text_view.getText();
		int len = text.length();
		if (len > max_length) {
			text = text.substring(len / 2);
			this.m_text_view.setText(text);
		}
	}

}
