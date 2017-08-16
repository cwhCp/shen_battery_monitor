package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.Monitor;
import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;
import com.puyatech.ab4c.shen_battery_monitor.model.ActionModel;
import com.puyatech.ab4c.shen_battery_monitor.tools.Utils;

public class SettingsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private SettingCombo setcom_zdcx;
	private SettingCombo setcom_qygd;
	private SettingCombo setcom_hfjt;
	private SettingCombo setcom_bcdl;

	private JButton button_sdcx;
	private JButton button_hfqs;
	private JButton button_gdfz;
	private JButton button_jtfz;
	private MonitorHolder m_holder;

	public SettingsPanel(MonitorHolder holder) {
		m_holder = holder;
	}

	public static SettingsPanel create(MonitorHolder holder) {
		SettingsPanel view = new SettingsPanel(holder);
		view.onCreate();
		return view;
	}


	private void onCreate() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(2, 2);
		gl.setVgap(10);
		gl.setHgap(10);
		panel.setLayout(gl);
		this.add(panel);

		this.button_sdcx = Utils.createButton(panel);
		this.button_hfqs = Utils.createButton(panel);
		this.button_gdfz = Utils.createButton(panel);
		this.button_jtfz = Utils.createButton(panel);

		this.setcom_zdcx = Utils.createSettingCombo(this);
		this.setcom_qygd = Utils.createSettingCombo(this);
		this.setcom_hfjt = Utils.createSettingCombo(this);
		this.setcom_bcdl = Utils.createSettingCombo(this);

	}

	public void initStringResources(StringRes res){
		this.button_sdcx.setText(res.getManualQuery());
		this.button_hfqs.setText(res.getRestoreDefaultValues());
		this.button_gdfz.setText(res.getLoadOff());
		this.button_jtfz.setText(res.getLoadConnected());

		this.setcom_zdcx.setTitle(res.getAutomaticQuery());
		this.setcom_zdcx.setButtonText(res.getSetup());

		this.setcom_qygd.setTitle(res.getValUnderVolShutdownLoad1());
		this.setcom_qygd.setButtonText(res.getSetup());
		this.setcom_qygd.setValue("10.8");

		this.setcom_hfjt.setTitle(res.getValRestoreLoad1());
		this.setcom_hfjt.setButtonText(res.getSetup());
		this.setcom_hfjt.setValue("11.8");

		this.setcom_bcdl.setTitle(res.getValBatNominal());
		this.setcom_bcdl.setButtonText(res.getOk());
		this.setcom_bcdl.setValue("0");
	}

	private void setupButton(JButton button, String action) {
		button.addActionListener(this);
		button.setActionCommand(action);
	}

	public void setupButtons(){
		this.setupButton(this.button_jtfz, ActionModel.jtfz);
		this.setupButton(this.button_gdfz, ActionModel.gdfz);
		this.setupButton(this.button_hfqs, ActionModel.hfqs);
		this.setupButton(this.button_sdcx, ActionModel.sdcx);

		this.setupButton(this.setcom_zdcx.button, ActionModel.auto_query);
		this.setupButton(this.setcom_qygd.button, ActionModel.set_qygdfz);
		this.setupButton(this.setcom_hfjt.button, ActionModel.set_hfjtfz);
		this.setupButton(this.setcom_bcdl.button, ActionModel.ok_bcdl);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		final String cmd = event.getActionCommand();
		if (cmd == null) {
		}
		else if (cmd.equals(ActionModel.jtfz)) {
			this.doActionJTFZ();
		} else if (cmd.equals(ActionModel.gdfz)) {
			this.doActionGDFZ();
		} else if (cmd.equals(ActionModel.hfqs)) {
			this.doActionHFQS();
		} else if (cmd.equals(ActionModel.sdcx)) {
			this.doActionSDCX();
		} else if (cmd.equals(ActionModel.auto_query)) {
			this.doActionAutoQuery();
		} else if (cmd.equals(ActionModel.set_qygdfz)) {
			this.doActionSetQYGDFZ();
		} else if (cmd.equals(ActionModel.set_hfjtfz)) {
			this.doActionSetHFJTFZ();
		} else if (cmd.equals(ActionModel.ok_bcdl)) {
			this.doActionOkBCDL();
		}
	}

	private void doActionJTFZ() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATO");
	}

	private void doActionGDFZ() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATC");
	}

	private void doActionHFQS() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATZ");
	}

	private void doActionSDCX() {
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATG", 0, 0);
		monitor.send("ATG");
	}

	private void doActionOkBCDL() {
		// TODO Auto-generated method stub
		// String s = this.setcom_bcdl.getValue();
		// Monitor monitor = this.m_holder.getMonitor();
		// monitor.send("ATxxx" + s);
	}

	private void doActionSetHFJTFZ() {
		String s = this.setcom_hfjt.getValue();
		if (s.indexOf('.') < 0) {
			s = s + ".00";
		}
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATH" + s);
	}

	private void doActionSetQYGDFZ() {
		String s = this.setcom_qygd.getValue();
		if (s.indexOf('.') < 0) {
			s = s + ".00";
		}
		Monitor monitor = this.m_holder.getMonitor();
		monitor.send("ATL" + s);
	}

	private void doActionAutoQuery() {
		String s = this.setcom_zdcx.getValue();
		int n = Integer.parseInt(s);
		n = Math.max(n, 2);
		Monitor monitor = this.m_holder.getMonitor();
		System.out.println(monitor);
		monitor.send("ATG", 1000, n * 1000);
		this.setcom_zdcx.setValue(n + "");
	}
}
