package com.puyatech.ab4c.shen_battery_monitor.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class AlarmDialog {

	public interface Sound {
		String dl = "1_BEEP2.WAV";
		String qy = "2_ALARM8.WAV";
	}

	public interface Icon {
		int qy = 1;
		int dl = 2;
	}

	public static class Builder {

		private String title;
		private String message;
		private int messageType;
		private Component parent;

		private String sound;
		private int icon;

		public AlarmDialog create() {

			switch (icon) {
			case Icon.dl:
				this.messageType = JOptionPane.WARNING_MESSAGE;
				break;
			case Icon.qy:
				this.messageType = JOptionPane.ERROR_MESSAGE;
				break;
			default:
				break;
			}

			return new AlarmDialog(this);
		}

		public void setSound(String sound) {
			this.sound = sound;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public String getSound() {
			return sound;
		}

		public int getIcon() {
			return icon;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getMessageType() {
			return messageType;
		}

		public void setMessageType(int messageType) {
			this.messageType = messageType;
		}

		public Component getParent() {
			return parent;
		}

		public void setParent(Component parent) {
			this.parent = parent;
		}

	}

	private Builder builder;

	public AlarmDialog(Builder builder) {
		this.builder = builder;
	}

	public void show() {

		SoundLoop sl = null;

		try {
			System.out.println(this + "begin");
			sl = new SoundLoop(this, this.builder.sound);
			sl.start();

			Component parent = builder.parent;
			Object message = builder.message;
			String title = builder.title;
			int messageType = builder.messageType;

			JOptionPane.showMessageDialog(parent, message, title, messageType);

		} finally {
			sl.stop();
			System.out.println(this + "end");
		}

	}
}
