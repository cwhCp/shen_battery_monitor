package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.Monitor;
import com.puyatech.ab4c.shen_battery_monitor.MonitorHolder;
import com.puyatech.ab4c.shen_battery_monitor.MonitorState;
import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private PropertyCombo combo_id;
	private PropertyCombo combo_dqdy;
	private PropertyCombo combo_dqdl;
	private PropertyCombo combo_dcwd;
	private PropertyCombo combo_xpwd;
	private PropertyCombo combo_fztd;
	private PropertyCombo combo_qygd;
	private PropertyCombo combo_hfjt;
	private PropertyCombo combo_zgl;
	private PropertyCombo combo_zdz;
	private PropertyCombo combo_dcdl;
	private PropertyCombo combo_dlbfb;
	private MonitorHolder m_holder;

	public StatusPanel(MonitorHolder holder) {
		m_holder = holder;
	}

	public static StatusPanel create(MonitorHolder holder) {
		StatusPanel view = new StatusPanel(holder);
		view.onCreate();
		return view;
	}

	private PropertyCombo createPropertyCombo(){
		PropertyCombo combo = PropertyCombo.create();
		combo.setValue("-");
		this.add(combo);
		return combo;
	}

	private void onCreate() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.combo_id = this.createPropertyCombo();
		this.combo_dqdy = this.createPropertyCombo();
		this.combo_dqdl = this.createPropertyCombo();
		this.combo_dcwd = this.createPropertyCombo();
		this.combo_xpwd = this.createPropertyCombo();
		this.combo_fztd = this.createPropertyCombo();
		this.combo_qygd = this.createPropertyCombo();
		this.combo_hfjt = this.createPropertyCombo();
		this.combo_zgl = this.createPropertyCombo();
		this.combo_zdz = this.createPropertyCombo();
		this.combo_dcdl = this.createPropertyCombo();
		this.combo_dlbfb = this.createPropertyCombo();
	}

	public void initStringResources(StringRes res){
		this.combo_id.setName(res.getIdBoard());
		this.combo_dqdy.setName(res.getValVoltage());
		this.combo_dqdl.setName(res.getValCurrent());
		this.combo_dcwd.setName(res.getTempBattery());
		this.combo_xpwd.setName(res.getTempChip());
		this.combo_fztd.setName(res.getValLoadState());
		this.combo_qygd.setName(res.getValUnderVolShutdownLoad2());
		this.combo_hfjt.setName(res.getValRestoreLoad2());
		this.combo_zgl.setName(res.getValTotalPower());
		this.combo_zdz.setName(res.getValTotalResistance());
		this.combo_dcdl.setName(res.getValBatteryCapacity());
		this.combo_dlbfb.setName(res.getValPOE());
	}

	public void updateActionUI(){
		Monitor monitor = this.m_holder.getMonitor();
		MonitorState state = monitor.getState();
		if (state == null) {
			return;
		}

		Map<String, StatusField> table = state.getFields();

		this.combo_id.setValue(id2string(state.getId()));
		this.updateComponent(table, "VOL", this.combo_dqdy);
		this.updateComponent(table, "CUR", this.combo_dqdl);
		this.updateComponent(table, "BAT", this.combo_dcwd);
		this.updateComponent(table, "CHIP", this.combo_xpwd);

		this.updateComponent(table, "REL", this.combo_fztd, "b");
		this.updateComponent(table, "CLO", this.combo_qygd);
		this.updateComponent(table, "OPE", this.combo_hfjt);
		this.updateComponent(table, "zgl", this.combo_zgl);
		this.updateComponent(table, "zdz", this.combo_zdz);
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
