package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;

public class SoundLoop {

	private Thread m_thread;
	private byte[] m_data;
	private boolean m_stop;

	public SoundLoop(Object ref, String name) {
		InputStream in = null;
		try {
			in = ref.getClass().getResourceAsStream(name);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream out = baos;
			IOTools.pump(in, out, null);
			this.m_data = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOTools.close(in);
		}
	}

	public void start() {
		MyRun run = new MyRun(m_data);
		Thread thread = (new Thread(run));
		this.m_thread = thread;
		thread.start();
	}

	public void stop() {
		this.m_thread.interrupt();
		this.m_thread = null;
		this.m_stop = true;
	}

	private class MyRun implements Runnable {

		private byte[] m_data;

		public MyRun(byte[] data) {
			this.m_data = data;
		}

		@Override
		public void run() {

			AudioInputStream ais = null;
			SourceDataLine sdl = null;
			InputStream in = null;

			try {

				in = this.getInput();

				ais = AudioSystem.getAudioInputStream(in);
				AudioFormat af = ais.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);

				sdl = (SourceDataLine) AudioSystem.getLine(info);
				sdl.open(af);
				sdl.start();

				// play

				byte[] buffer = new byte[1024 * 20];
				for (; !SoundLoop.this.m_stop;) {
					int cb = ais.read(buffer);
					if (cb < 0) {
						ais.reset();
						// Thread.sleep(1000);
						continue;
					} else {
						sdl.write(buffer, 0, cb);
					}
				}

			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();

			} finally {
				this.close(ais);
				this.close(sdl);
				IOTools.close(in);
			}

		}

		private InputStream getInput() {
			return new ByteArrayInputStream(m_data);
		}

		private void close(SourceDataLine sdl) {
			sdl.stop();
			sdl.close();
		}

		private void close(AudioInputStream ais) {
			try {
				ais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
