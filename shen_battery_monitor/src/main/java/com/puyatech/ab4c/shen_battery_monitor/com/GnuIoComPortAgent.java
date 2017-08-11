package com.puyatech.ab4c.shen_battery_monitor.com;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;

public class GnuIoComPortAgent implements ComPortAgent {

	private Map<String, CommPortIdentifier> m_ports;

	@Override
	public ComPort open(String com_name) throws IOException {

		CommPortIdentifier pid = this.m_ports.get(com_name);
		if (pid == null) {
			throw new IOException("no port with name: " + com_name);
		}

		try {
			CommPort port = pid.open("", 2000);
			return new MyComPort(port);
		} catch (PortInUseException e) {
			throw new IOException(e);
		}

	}

	@Override
	public void close(ComPort com) {
		if (com != null) {
			com.close();
		}
	}

	@Override
	public String[] listPorts() {
		Map<String, CommPortIdentifier> table = new Hashtable<String, CommPortIdentifier>();
		@SuppressWarnings("rawtypes")
		Enumeration e = CommPortIdentifier.getPortIdentifiers();
		for (; e.hasMoreElements();) {
			CommPortIdentifier portId = (CommPortIdentifier) e.nextElement();
			String name = portId.getName();
			table.put(name, portId);
		}
		this.m_ports = table;
		Set<String> keys = table.keySet();
		return keys.toArray(new String[keys.size()]);
	}

	private class MyComPort implements ComPort {

		private InputStream in;
		private OutputStream out;
		private CommPort com_port;

		public MyComPort(CommPort port) throws IOException {
			this.com_port = port;
			this.in = port.getInputStream();
			this.out = port.getOutputStream();
		}

		@Override
		public InputStream getInput() {
			return this.in;
		}

		@Override
		public OutputStream getOutput() {
			return this.out;
		}

		@Override
		public void close() {
			IOTools.close(in);
			IOTools.close(out);
			this.com_port.close();
		}
	}
}
