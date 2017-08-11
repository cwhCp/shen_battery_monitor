package com.puyatech.ab4c.shen_battery_monitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class SimpleInputReader extends Reader {

	private final InputStream in;

	public SimpleInputReader(InputStream in, String enc) {
		this.in = in;
	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		final int end = off + len;
		int cc = 0;
		for (int i = off; i < end; i++) {
			final int ch = this.read();
			if (ch < 0) {
				if (cc > 0) {
					return cc;
				} else {
					return -1;
				}
			} else {
				cbuf[i] = (char) ch;
				cc++;
			}
		}
		return cc;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

}
