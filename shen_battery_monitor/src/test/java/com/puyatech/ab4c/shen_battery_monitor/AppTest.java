package com.puyatech.ab4c.shen_battery_monitor;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.junit.Test;

import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;

public class AppTest {

	/***
	 * @param abc
	 *            unit: metres
	 */

	@Test
	public void testApp() {
	}

	public static void main(String[] args) {
		try {
			AppTest at = new AppTest();
			at.openCom(4);
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public File findFile(String name) throws FileNotFoundException {
		TheFileFinder tff = new TheFileFinder();
		File file = tff.file(name);
		return file;
	}

	public InputStream openCom(int i) throws PortInUseException, IOException {

		InputStream in = null;
		OutputStream out = null;

		@SuppressWarnings("rawtypes")
		Enumeration e = CommPortIdentifier.getPortIdentifiers();
		for (; e.hasMoreElements();) {

			CommPortIdentifier portId = (CommPortIdentifier) e.nextElement();

			System.out.println("\r");

			System.out.println("找到端口： " + portId.getName());

			String port = "COM"; // "todo..."; // TODO
			if (portId.getName().startsWith(port)) {

				String appName = this.getClass().getSimpleName();
				int timeout = 5000;
				CommPort cp = portId.open(appName, timeout);
				in = cp.getInputStream();
				out = cp.getOutputStream();

				this.testIO(in, out);

				break;
			}

		}

		return in;
	}

	private void testIO(InputStream in, OutputStream out) throws IOException {

		byte[] ba = "ATG\r\n".getBytes();
		out.write(ba);
		System.out.write(ba);

		byte[] buffer = new byte[1];
		IOTools.pump(in, System.out, buffer);

	}

}
