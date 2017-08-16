package com.puyatech.ab4c.shen_battery_monitor.tools;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puyatech.ab4c.shen_battery_monitor.gui.PropertyCombo;
import com.puyatech.ab4c.shen_battery_monitor.gui.SettingCombo;

public class Utils {

    public static String id2string(long id) {
        return Long.toHexString(id);
    }

    public static JButton createButton(JPanel panel){
        JButton button = new JButton("button");
        panel.add(button);
        return button;
    }
    
    public static SettingCombo createSettingCombo(JPanel panel){
		SettingCombo combo = SettingCombo.create();
		panel.add(combo);
		// combo.setValue("2");
		return combo;
	}
    

    public static JLabel createCenterLabel(String text){
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

    public static PropertyCombo createPropertyCombo(JPanel panel){
		PropertyCombo combo = PropertyCombo.create();
		combo.setValue("-");
		panel.add(combo);
		return combo;
    }
}