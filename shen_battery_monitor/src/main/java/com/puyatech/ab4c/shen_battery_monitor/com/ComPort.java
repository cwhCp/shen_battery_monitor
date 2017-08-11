package com.puyatech.ab4c.shen_battery_monitor.com;

import java.io.InputStream;
import java.io.OutputStream;

public interface ComPort {

	InputStream getInput();

	OutputStream getOutput();

	void close();

}
