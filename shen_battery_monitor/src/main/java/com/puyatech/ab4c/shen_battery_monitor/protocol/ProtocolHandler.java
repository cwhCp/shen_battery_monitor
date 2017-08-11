package com.puyatech.ab4c.shen_battery_monitor.protocol;

public interface ProtocolHandler {

	void on_ok();

	void on_status(long id, BatteryStatus status);

}
