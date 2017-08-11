package com.puyatech.ab4c.shen_battery_monitor.gui;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class BorderUtils {

	public static Border getBorder(Object user) {
		// TODO Auto-generated method stub

		String name = user.getClass().getSimpleName();

		Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);

		return border;
	}

}
