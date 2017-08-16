package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.puyatech.ab4c.shen_battery_monitor.Monitor;
import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;
import com.puyatech.ab4c.shen_battery_monitor.MonitorState;
import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;

public class MainFrame extends JFrame implements ActionListener {

	interface Action {
		String start = "start";
		String stop = "stop";

		String on_off = "on_off";
		String jtfz = "jtfz";
		String gdfz = "gdfz";
		String hfqs = "hfqs";
		String sdcx = "sdcx";

		String auto_query = "auto_query";
		String set_qygdfz = "set_gdfz";
		String set_hfjtfz = "set_jtfz";
		String ok_bcdl = "ok_bcdl";
	}

	private static final long serialVersionUID = 1L;
	private final MonitorHolder m_holder;

	private ControlPanel control;
	private SettingsPanel settings;
	private StatusPanel status;
	private ConsolePanel console;
	private MyUIUpdater m_ui_updater;
	private StringRes m_res;

	public MainFrame(MonitorHolder holder) {
		this.m_holder = holder;
	}
	
	public void onCreate() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(this.getClass().getName());
		this.setIconImage(new ImageIcon(this.getClass().getResource("monitor.png")).getImage());
		this.setSize(600, 600);
		this.setJMenuBar(this.createMenuBar());
		this.setResizable(false);

		this.control = ControlPanel.create(this.m_holder);
		this.settings = SettingsPanel.create(this.m_holder);
		this.status = StatusPanel.create(this.m_holder);
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

		this.setupButton(settings.button_sdcx, Action.sdcx);
		this.setupButton(settings.button_hfqs, Action.hfqs);
		this.setupButton(settings.button_gdfz, Action.gdfz);
		this.setupButton(settings.button_jtfz, Action.jtfz);

		this.setupButton(settings.setcom_zdcx.button, Action.auto_query);
		this.setupButton(settings.setcom_qygd.button, Action.set_qygdfz);
		this.setupButton(settings.setcom_hfjt.button, Action.set_hfjtfz);
		this.setupButton(settings.setcom_bcdl.button, Action.ok_bcdl);

		this.setupButton(control.button_on_off, Action.on_off);

	}

	private void setupButton(JButton button, String action) {
		button.addActionListener(this);
		button.setActionCommand(action);
	}

	private void initStringResources() {

		StringRes res = this.m_holder.getStringRes();

		this.setTitle(res.getTitle());

		// control
		control.label_com.setText(res.getComPort());
		control.label_config.setText("9600-8-N-1");
		control.label_power.setText("");

		control.button_on_off.setText(res.getOnOff());

		ComboBoxModel<String> model = this.createComPortComboModel();
		control.combo_port.setModel(model);

		// settings

		settings.button_sdcx.setText(res.getManualQuery());
		settings.button_hfqs.setText(res.getRestoreDefaultValues());
		settings.button_gdfz.setText(res.getLoadOff());
		settings.button_jtfz.setText(res.getLoadConnected());

		settings.setcom_zdcx.setTitle(res.getAutomaticQuery());
		// settings.setcom_zdcx.setUnit("s");
		settings.setcom_zdcx.setButtonText(res.getSetup());

		settings.setcom_qygd.setTitle(res.getValUnderVolShutdownLoad1());
		// settings.setcom_qygd.setUnit("V");
		settings.setcom_qygd.setButtonText(res.getSetup());
		settings.setcom_qygd.setValue("10.8");

		settings.setcom_hfjt.setTitle(res.getValRestoreLoad1());
		// settings.setcom_hfjt.setUnit("V");
		settings.setcom_hfjt.setButtonText(res.getSetup());
		settings.setcom_hfjt.setValue("11.8");

		settings.setcom_bcdl.setTitle(res.getValBatNominal());
		// settings.setcom_bcdl.setUnit("mAh");
		settings.setcom_bcdl.setButtonText(res.getOk());
		settings.setcom_bcdl.setValue("0");

		// status

		status.combo_id.setName(res.getIdBoard());
		status.combo_dqdy.setName(res.getValVoltage());
		status.combo_dqdl.setName(res.getValCurrent());
		status.combo_dcwd.setName(res.getTempBattery());
		status.combo_xpwd.setName(res.getTempChip());
		status.combo_fztd.setName(res.getValLoadState());
		status.combo_qygd.setName(res.getValUnderVolShutdownLoad2());
		status.combo_hfjt.setName(res.getValRestoreLoad2());
		status.combo_zgl.setName(res.getValTotalPower());
		status.combo_zdz.setName(res.getValTotalResistance());
		status.combo_dcdl.setName(res.getValBatteryCapacity());
		status.combo_dlbfb.setName(res.getValPOE());

		this.m_res = res;
	}

	private ComboBoxModel<String> createComPortComboModel() {
		ComPortAgent agent = this.m_holder.getComPortAgent();
		String[] ports = agent.listPorts();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String name : ports) {
			model.addElement(name);
		}
		return model;
	}

	private JMenuBar createMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = this.createMenu("file");
		menu.add(this.createMenuItem(Action.start, null));
		menu.add(this.createMenuItem(Action.stop, null));
		mb.add(menu);
		return null; // mb;
	}

	private JMenuItem createMenuItem(String command, String label) {
		if (label == null) {
			label = command;
		}
		JMenuItem item = new JMenuItem();
		item.setText(label);
		item.setActionCommand(command);
		item.addActionListener(this);
		return item;
	}

	private JMenu createMenu(String text) {
		JMenu menu = new JMenu();
		menu.setText(text);
		return menu;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		final String cmd = event.getActionCommand();
		if (cmd == null) {
		} else if (cmd.equals(Action.start)) {
			this.doActionStart();
		} else if (cmd.equals(Action.stop)) {
			this.doActionStop();
		} else if (cmd.equals(Action.on_off)) {
			this.doActionOnOff();
		} else if (cmd.equals(Action.jtfz)) {
			this.doActionJTFZ();
		} else if (cmd.equals(Action.gdfz)) {
			this.doActionGDFZ();
		} else if (cmd.equals(Action.hfqs)) {
			this.doActionHFQS();
		} else if (cmd.equals(Action.sdcx)) {
			this.doActionSDCX();
		} else if (cmd.equals(Action.auto_query)) {
			this.doActionAutoQuery();
		} else if (cmd.equals(Action.set_qygdfz)) {
			this.doActionSetQYGDFZ();
		} else if (cmd.equals(Action.set_hfjtfz)) {
			this.doActionSetHFJTFZ();
		} else if (cmd.equals(Action.ok_bcdl)) {
			this.doActionOkBCDL();
		}
	}

	private void doActionOkBCDL() {
		// TODO Auto-generated method stub
		// String s = this.settings.setcom_bcdl.getValue();
		// Monitor monitor = this.m_holder.getMonitor();
		// monitor.send("ATxxx" + s);
	}

	private void doActionSetHFJTFZ() {
		String s = this.settings.setcom_hfjt.getValue();
		if (s.indexOf('.') < 0) {
			s = s + ".00";
		}
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATH" + s);
	}

	private void doActionSetQYGDFZ() {
		String s = this.settings.setcom_qygd.getValue();
		if (s.indexOf('.') < 0) {
			s = s + ".00";
		}
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATL" + s);
	}

	private void doActionAutoQuery() {
		String s = this.settings.setcom_zdcx.getValue();
		int n = Integer.parseInt(s);
		n = Math.max(n, 2);
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATG", 1000, n * 1000);
		this.settings.setcom_zdcx.setValue(n + "");
	}

	private void doActionSDCX() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATG", 0, 0);
		monitor.send("ATG");
	}

	private void doActionHFQS() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATZ");
	}

	private void doActionGDFZ() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATC");
	}

	private void doActionJTFZ() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATO");
	}

	private void doActionOnOff() {
		Monitor monitor = this.m_holder.getMonitor();
		if (monitor.isStateOn()) {
			this.doActionStop();
		} else {
			this.doActionStart();
		}
	}

	private void doActionStart() {
		JComboBox<String> jcbox = control.combo_port;
		Object obj = jcbox.getSelectedItem();
		if (obj == null) {
			JOptionPane.showMessageDialog(this, "无选择对象", "提示", JOptionPane.ERROR_MESSAGE);
			System.out.println("selected empty");
			return;
		}
		String port = obj.toString();
		System.out.println("open " + port);
		Monitor monitor = this.m_holder.getMonitor();
		monitor.setComPortName(port);
		monitor.start();
		this.setCurrentUIUpdater(new MyUIUpdater());
		this.updateUI();
	}

	private void doActionStop() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.stop();
		this.setCurrentUIUpdater(null);
		this.updateUI();
	}

	private void setCurrentUIUpdater(MyUIUpdater updater) {
		MyUIUpdater old = this.m_ui_updater;
		this.m_ui_updater = updater;
		if (old != null) {
			old.kill();
		}
		if (updater != null) {
			updater.start();
		}
	}

	private class MyUIUpdater {

		private boolean m_do_stop;

		public void kill() {
			this.m_do_stop = true;
		}

		public void start() {
			Runnable run = new Runnable() {
				@Override
				public void run() {
					MyUIUpdater.this.loop();
				}
			};
			(new Thread(run)).start();
		}

		protected void loop() {
			Runnable run = new Runnable() {
				@Override
				public void run() {
					MyUIUpdater.this.onTimer();
				}
			};
			try {
				for (;;) {
					if (this.m_do_stop) {
						break;
					} else {
						SwingUtilities.invokeAndWait(run);
						Thread.sleep(1000);
					}
				}
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}
		}

		private void onTimer() {
			MainFrame.this.updateUI();
		}
	}

	public void updateUI() {

		Monitor monitor = this.m_holder.getMonitor();
		this.control.setStateIcon(monitor.isStateOn() ? 1 : 0);

		MonitorState state = monitor.getState();
		if (state == null) {
			return;
		}

		Map<String, StatusField> table = state.getFields();

		this.status.combo_id.setValue(this.id2string(state.getId()));
		this.updateComponent(table, "VOL", this.status.combo_dqdy);
		this.updateComponent(table, "CUR", this.status.combo_dqdl);
		this.updateComponent(table, "BAT", this.status.combo_dcwd);
		this.updateComponent(table, "CHIP", this.status.combo_xpwd);

		this.updateComponent(table, "REL", this.status.combo_fztd, "b");
		this.updateComponent(table, "CLO", this.status.combo_qygd);
		this.updateComponent(table, "OPE", this.status.combo_hfjt);
		this.updateComponent(table, "zgl", this.status.combo_zgl);
		this.updateComponent(table, "zdz", this.status.combo_zdz);

		this.checkAlarmState(monitor);

	}

	private void checkAlarmState(Monitor monitor) {

		// AlarmTrigger at_qy = new QYAlarmTrigger();
		// AlarmTrigger at_dl = new DLAlarmTrigger();

		MonitorState low = monitor.getAlarmBatLow();
		MonitorState low_close = monitor.getAlarmBatLowClose();

		if (low_close != null) {
			AlarmDialog.Builder builder = new AlarmDialog.Builder();
			builder.setSound(AlarmDialog.Sound.qy);
			builder.setIcon(AlarmDialog.Icon.qy);
			builder.setMessage(this.m_res.getBatLowClose());
			builder.create().show();
			monitor.setAlarmBatLowClose(null);
			monitor.setAlarmBatLow(null);
		} else if (low != null) {
			AlarmDialog.Builder builder = new AlarmDialog.Builder();
			builder.setSound(AlarmDialog.Sound.dl);
			builder.setIcon(AlarmDialog.Icon.dl);
			builder.setMessage(this.m_res.getBatLow());
			builder.create().show();
			monitor.setAlarmBatLow(null);
		}
	}

	private String id2string(long id) {
		return Long.toHexString(id);
	}

	private void updateComponent(Map<String, StatusField> table, String key, PropertyCombo com, String fmt) {
		StatusField field = table.get(key);
		if (com != null && field != null) {
			if (fmt.equals("b")) {
				double value = field.getValue();
				String s = (value < 0.5) ? "0" : "1";
				com.setValue(s);
			} else {
				this.updateComponent(table, key, com);
			}
		}
	}

	private void updateComponent(Map<String, StatusField> table, String key, PropertyCombo com) {
		StatusField field = table.get(key);
		if (com != null && field != null) {
			String unit = field.getUnit();
			double value = field.getValue();
			String s = Double.toString(value);
			if (s.length() > 6) {
				int i = s.indexOf('.');
				if (i > 0) {
					i += 4;
					if (i < s.length()) {
						s = s.substring(0, i);
					}
				}
			}
			com.setValue(s + " " + unit);
		}
	}

}
