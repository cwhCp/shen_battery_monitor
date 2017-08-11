package com.puyatech.ab4c.shen_battery_monitor.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;

public class TestingComPortAgent implements ComPortAgent {

	@Override
	public ComPort open(String name) {
		MyComPort com = new MyComPort();
		com.inner_open();
		return com;
	}

	@Override
	public void close(ComPort com) {
		if (com != null) {
			com.close();
		}
	}

	private class MyComPort implements ComPort {

		private OutputStream m_output;
		private InputStream m_input;
		private CloseableHttpClient m_client;
		private CloseableHttpResponse m_response;

		public void inner_open() {

			CloseableHttpClient client = null;
			CloseableHttpResponse resp = null;

			final String url = "http://localhost/test/sbm/UP_info.txt";

			try {

				client = HttpClients.createDefault();
				HttpGet req = new HttpGet(url);
				resp = client.execute(req);

				StatusLine status = resp.getStatusLine();
				int code = status.getStatusCode();
				String msg = status.getReasonPhrase();
				if (code != 200) {
					throw new RuntimeException("HTTP " + code + " " + msg);
				}

				HttpEntity ent = resp.getEntity();

				this.m_input = ent.getContent();
				this.m_output = new OutputStream() {
					@Override
					public void write(int b) throws IOException {
					}
				};

				this.m_client = client;
				this.m_response = resp;

				resp = null;
				client = null;

			} catch (Exception e) {
				System.err.println("throw error while open " + url);
				e.printStackTrace();

			} finally {
				HttpClientUtils.closeQuietly(resp);
				HttpClientUtils.closeQuietly(client);
			}
		}

		@Override
		public InputStream getInput() {
			return this.m_input;
		}

		@Override
		public OutputStream getOutput() {
			return this.m_output;
		}

		@Override
		public void close() {

			IOTools.close(this.m_input);
			IOTools.close(this.m_output);

			HttpClientUtils.closeQuietly(this.m_response);
			HttpClientUtils.closeQuietly(this.m_client);

		}
	}

	@Override
	public String[] listPorts() {
		String[] array = { "TCom1", "TCom4", "TCom7", };
		return array;
	}

}
