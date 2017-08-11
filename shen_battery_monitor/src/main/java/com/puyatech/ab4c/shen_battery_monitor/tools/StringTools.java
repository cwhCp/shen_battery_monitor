package com.puyatech.ab4c.shen_battery_monitor.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class StringTools {

	public static String loadString(InputStream in, char[] buffer, String enc)
			throws IOException {
		if (enc == null) {
			enc = "UTF-8";
		}
		Reader reader = null;
		try {
			reader = new InputStreamReader(in, enc);
			return loadString(reader, buffer);
		} finally {
			IOTools.close(reader);
		}
	}

	public static String loadString(Reader in, char[] buffer)
			throws IOException {
		if (buffer == null) {
			buffer = new char[1024];
		}
		StringBuilder sb = new StringBuilder();
		for (;;) {
			int cc = in.read(buffer);
			if (cc < 0) {
				break;
			} else {
				sb.append(buffer, 0, cc);
			}
		}
		return sb.toString();
	}

}
