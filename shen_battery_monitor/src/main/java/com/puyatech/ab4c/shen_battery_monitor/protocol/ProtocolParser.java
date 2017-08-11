package com.puyatech.ab4c.shen_battery_monitor.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProtocolParser {

	public void parse(InputStream in, ProtocolHandler h) throws IOException, InterruptedException {
		Set<Integer> sub_charset = this.makeSubCharset();
		StringBuilder buffer = new StringBuilder();
		for (;;) {
			final int b = in.read();
			if (b < 0) {
				// break;
				Thread.sleep(100);
			}
			if (!sub_charset.contains(b)) {
				continue;
			}
			final char ch = (char) b;
			if (ch == 0x0a || ch == 0x0d) {
				String s = buffer.toString().trim();
				buffer.setLength(0);
				if (s.length() > 0) {
					this.parse_string(s, h);
				}
			} else {
				buffer.append(ch);
			}
		}
	}

	private Set<Integer> makeSubCharset() {
		StringBuilder sb = new StringBuilder();
		sb.append("abcdefghijklmnopqrstuvwxyz");
		sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		sb.append("0123456789");
		sb.append("+-.=,$\r\n");
		Set<Integer> sub_charset = new HashSet<Integer>();
		char[] chs = sb.toString().toCharArray();
		for (char ch : chs) {
			sub_charset.add((int) ch);
		}
		return sub_charset;
	}

	private void parse_string(String s, ProtocolHandler h) {
		final int iS = s.indexOf('$');
		final String s1, s2;
		if (iS < 0) {
			s1 = s;
			s2 = null;
		} else {
			s1 = s.substring(0, iS);
			s2 = s.substring(iS);
		}
		if (s1.contains("OK")) {
			// System.out.println(s1);
			h.on_ok();
		}
		if (s2 != null) {
			List<StatusField> fields = new ArrayList<StatusField>();
			String[] array = s2.split(",");
			long id = 0;
			for (int i = 0; i < array.length; i++) {
				final String s3 = array[i];
				if (i == 0) {
					id = this.parse_id(s3);
				} else {
					fields.add(this.parse_field(s3));
				}
			}
			h.on_status(id, new BatteryStatus(fields));
		}
	}

	private StatusField parse_field(String s) {
		final int i1 = s.indexOf('=');
		if (i1 > 0) {
			final int end = s.length();
			final String num_set = "+-.0123456789";
			int i = 0;
			for (i = i1 + 1; i < end; i++) {
				final char ch = s.charAt(i);
				if (num_set.indexOf(ch) < 0) {
					break;
				} else {
					continue;
				}
			}
			final int i2 = i;
			final String s1, s2, s3;
			s1 = s.substring(0, i1).trim();
			s2 = s.substring(i1 + 1, i2).trim();
			s3 = s.substring(i2).trim();

			try {
				return new StatusField(s1, Double.parseDouble(s2), s3);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	private long parse_id(String s) {
		s = s.substring(1);
		return Long.parseLong(s, 16);
	}
}
