package com.puyatech.ab4c.shen_battery_monitor.com;

import java.io.IOException;

public interface ComPortAgent {

	/***
	 * @param com_name
	 *            ['COM1','COM2','COM3'...]
	 * @throws IOException
	 * */

	ComPort open(String com_name) throws IOException;

	void close(ComPort com);

	String[] listPorts();

}
