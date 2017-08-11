package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;
import com.puyatech.ab4c.shen_battery_monitor.tools.StringTools;

public class StringResLoader {

	public static StringRes load(String name) {
		Throwable err = null;
		InputStream in = null;
		try {
			in = StringRes.class.getResourceAsStream(name);
			if (in == null) {
				throw new RuntimeException("cannot find res: " + name);
			}
			String s = StringTools.loadString(in, null, null);
			int i1 = s.indexOf('{');
			int i2 = s.lastIndexOf('}');
			s = s.substring(i1, i2 + 1);
			Gson gson = new Gson();
			StringRes res = gson.fromJson(s, StringRes.class);
			return res;
		} catch (IOException e) {
			err = e;
		} finally {
			IOTools.close(in);
		}
		throw new RuntimeException(err);
	}

}
