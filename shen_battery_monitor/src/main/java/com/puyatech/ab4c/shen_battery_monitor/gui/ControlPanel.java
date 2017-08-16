package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.puyatech.ab4c.shen_battery_monitor.Monitor;
import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;
import com.puyatech.ab4c.shen_battery_monitor.MonitorState;
import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.model.ActionModel;


public class ControlPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private MonitorHolder m_holder;

	private JButton button_on_off;
	private JComboBox<String> combo_port;
	private JLabel label_power;
	private JLabel label_com;
	private JLabel label_config;
	private StatusPanel m_status;

	private MyUIUpdater m_ui_updater;
	private StringRes m_res;


	public ControlPanel(MonitorHolder holder) {
		m_holder = holder;
	}

	public static ControlPanel create(MonitorHolder holder) {
		ControlPanel view = new ControlPanel(holder);
		view.onCreate();
		return view;
	}

	public void setStatusPanel(StatusPanel status){
		m_status = status;
	}

	private JLabel createCenterLabel(String text, String iconName){
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

		this.label_com = this.createCenterLabel("label-1", null);
		this.combo_port = new JComboBox<String>();
		this.label_config = this.createCenterLabel("label-2", null);
		this.button_on_off = new JButton();
		this.button_on_off.setText("button");
		this.label_power = this.createCenterLabel("", "red.png");

		this.add(this.label_com);
		this.add(this.combo_port);
		this.add(this.label_config);
		this.add(this.button_on_off);
		this.add(this.label_power);

		int lr = 10;
		int tb = 10;
		this.setBorder(BorderFactory.createEmptyBorder(tb, lr, tb, lr));
	}

	public void setStateIcon(int index) {
		this.label_power.setIcon(new ImageIcon(this.getClass().getResource(index == 0? "red.png" :"green.png")));
	}

	public void setInfoByRes(StringRes res){
		label_com.setText(res.getComPort());
		label_config.setText("9600-8-N-1");
		label_power.setText("");
		button_on_off.setText(res.getOnOff());
	}

	public void setComboPort(ComboBoxModel<String> model){
		combo_port.setModel(model);
	}

	public void initStringResources(StringRes res){
		this.label_com.setText(res.getComPort());
		this.label_config.setText("9600-8-N-1");
		this.label_power.setText("");
		this.button_on_off.setText(res.getOnOff());
		ComboBoxModel<String> model = this.createComPortComboModel();
		this.combo_port.setModel(model);
		this.m_res = res;
	}

	private ComboBoxModel<String> createComPortComboModel() {
		ComPortAgent agent = m_holder.getComPortAgent();
		String[] ports = agent.listPorts();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String name : ports) {
			model.addElement(name);
		}
		return model;
	}

	public void setupButtons(){
		this.button_on_off.addActionListener(this);
		this.button_on_off.setActionCommand(ActionModel.on_off);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		final String cmd = event.getActionCommand();
		if (cmd == null) {
		// } else if (cmd.equals(ActionModel.start)) {
		// 	this.doActionStart();
		// } else if (cmd.equals(ActionModel.stop)) {
		// 	this.doActionStop();
		} else if (cmd.equals(ActionModel.on_off)) {
			this.doActionOnOff();
		}
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
		Object obj = this.combo_port.getSelectedItem();
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
		this.updateActionUI();
	}

	private void doActionStop() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.stop();
		this.setCurrentUIUpdater(null);
		this.updateActionUI();
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

	public void updateActionUI() {
		Monitor monitor = this.m_holder.getMonitor();
		setStateIcon(monitor.isStateOn() ? 1 : 0);

		this.m_status.updateActionUI();
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
			ControlPanel.this.updateActionUI();
		}
	}

}
