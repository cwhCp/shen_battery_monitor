package com.puyatech.ab4c.shen_battery_monitor;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class TheFileFinder {

	private File m_base;

	public File basepath() {
		File base = this.m_base;
		if (base == null) {
			this.m_base = base = this.findBasePath();
		}
		return base;
	}

	private File findBasePath() {
		try {
			URI uri = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI();
			final File path0 = new File(uri);
			File path = path0;

			String name = this.getClass().getName();
			for (; path != null; path = path.getParentFile()) {
				File dir = new File(path, name);
				if (dir.exists() && dir.isDirectory()) {
					path = dir;
					break;
				}
			}
			if (path == null) {
				String msg = "cannot find '" + name + "' in path of '" + path0
						+ "' .";
				throw new RuntimeException(msg);
			}

			return path;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} finally {
		}
	}

	public File file(String name) {
		File dir = this.basepath();
		return new File(dir, name);
	}

}
