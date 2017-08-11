package com.puyatech.ab4c.shen_battery_monitor.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOTools {

	public static void close(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Reader stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Writer stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void pump(InputStream in, OutputStream out, byte[] buffer)
			throws IOException {
		if (buffer == null) {
			buffer = new byte[1024];
		}
		for (;;) {
			int cb = in.read(buffer);
			if (cb < 0) {
				break;
			} else {
				out.write(buffer, 0, cb);
			}
		}
	}

}
