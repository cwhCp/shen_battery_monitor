package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.Writer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.JMenu;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	private ControlPanel control;
	private SettingsPanel settings;
	private StatusPanel status;
	private ConsolePanel console;

	public MainFrame(MonitorHolder holder) {
		this.m_holder = holder;
	}
	
	public void onCreate() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(this.getClass().getResource("monitor.png")).getImage());
		this.setSize(600, 600);
		// this.setJMenuBar(this.createMenuBar());
		this.setResizable(false);

		this.control = ControlPanel.create(this.m_holder);
		this.settings = SettingsPanel.create(this.m_holder);
		this.status = StatusPanel.create(this.m_holder);
		this.control.setStatusPanel(this.status);
		this.console = ConsolePanel.create(this.m_holder);

		Container client = this.getContentPane();
		client.setLayout(new BorderLayout());
		client.add(control, BorderLayout.NORTH);
		{
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(1, 2));
			center.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

			center.add(settings, BorderLayout.WEST);
			center.add(status, BorderLayout.CENTER);

			client.add(center, BorderLayout.CENTER);
		}
		client.add(console, BorderLayout.SOUTH);

		this.initStringResources();
		this.setupButtons();
		this.setupLogOutput();
	}

	private void setupLogOutput() {
		ConsolePanel console = this.console;
		Writer out = new ConsolePanelWriter(console);
		this.m_holder.getMonitor().setLogOut(out);
	}

	private void setupButtons() {
		settings.setupButtons();
		control.setupButtons();
	}

	private void initStringResources() {
		StringRes res = this.m_holder.getStringRes();
		this.setTitle(res.getTitle());
		// control
		control.initStringResources(res);
		// settings
		settings.initStringResources(res);
		// status
		status.initStringResources(res);
	}

	// private JMenuBar createMenuBar() {
	// 	JMenuBar mb = new JMenuBar();
	// 	JMenu menu = this.createMenu("file");
	// 	menu.add(this.createMenuItem(ActionModel.start, null));
	// 	menu.add(this.createMenuItem(ActionModel.stop, null));
	// 	mb.add(menu);
	// 	return null; // mb;
	// }

	// private JMenuItem createMenuItem(String command, String label) {
	// 	if (label == null) {
	// 		label = command;
	// 	}
	// 	JMenuItem item = new JMenuItem();
	// 	item.setText(label);
	// 	item.setActionCommand(command);
	// 	item.addActionListener(this);
	// 	return item;
	// }

//	private JMenu createMenu(String text) {
//		JMenu menu = new JMenu();
//		menu.setText(text);
//		return menu;
//	}

	// @Override
	// public void actionPerformed(ActionEvent event) {
	// 	final String cmd = event.getActionCommand();
	// 	if (cmd == null) {
	// 	} else if (cmd.equals(ActionModel.start)) {
	// 		this.doActionStart();
	// 	} else if (cmd.equals(ActionModel.stop)) {
	// 		this.doActionStop();
	// 	} else if (cmd.equals(ActionModel.on_off)) {
	// 		this.doActionOnOff();
	// 	}
	// }
}
