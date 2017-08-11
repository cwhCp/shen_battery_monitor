package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

public class ConsolePanelWriter extends Writer {

	private final ConsolePanel console;

	public ConsolePanelWriter(ConsolePanel console) {
		this.console = console;
	}

	@Override
	public synchronized void write(char[] cbuf, int off, int len)
			throws IOException {
		try {
			String s = new String(cbuf, off, len);
			MyRunnable run = new MyRunnable(console, s);
			javax.swing.SwingUtilities.invokeAndWait(run);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {
	}

	private class MyRunnable implements Runnable {

		private ConsolePanel console;
		private String string;

		public MyRunnable(ConsolePanel console, String s) {
			this.console = console;
			this.string = s;
		}

		@Override
		public void run() {
			this.console.append(string);
		}
	}

}
