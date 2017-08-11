package com.puyatech.ab4c.shen_battery_monitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.puyatech.ab4c.shen_battery_monitor.com.ComPort;
import com.puyatech.ab4c.shen_battery_monitor.com.ComPortAgent;
import com.puyatech.ab4c.shen_battery_monitor.protocol.BatteryStatus;
import com.puyatech.ab4c.shen_battery_monitor.protocol.ProtocolHandler;
import com.puyatech.ab4c.shen_battery_monitor.protocol.ProtocolParser;
import com.puyatech.ab4c.shen_battery_monitor.protocol.StatusField;
import com.puyatech.ab4c.shen_battery_monitor.tools.IOTools;

public class Monitor implements Runnable {

	private MyRuntime m_current_runtime;
	private final ComPortAgent m_agent;

	private String comPortName;
	private MonitorState state;
	private Writer m_log_out;

	private MonitorState alarmBatLow;
	private MonitorState alarmBatLowClose;

	public Monitor(ComPortAgent agent) {
		this.m_agent = agent;
	}

	public MonitorState getAlarmBatLow() {
		return alarmBatLow;
	}

	public void setAlarmBatLow(MonitorState alarmBatLow) {
		this.alarmBatLow = alarmBatLow;
	}

	public MonitorState getAlarmBatLowClose() {
		return alarmBatLowClose;
	}

	public void setAlarmBatLowClose(MonitorState alarmBatLowClose) {
		this.alarmBatLowClose = alarmBatLowClose;
	}

	public String getComPortName() {
		return comPortName;
	}

	public void setComPortName(String comPortName) {
		this.comPortName = comPortName;
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		this.setCurrentRuntime(null);
	}

	@Override
	public void run() {
		try {
			MyRuntime rt = new MyRuntime();
			this.setCurrentRuntime(rt);
			rt.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCurrentRuntime(MyRuntime rt) {
		MyRuntime old = this.selectCurrentRuntime(rt);
		if (old != null) {
			old.close();
		}
		if (rt != null) {
			rt.open();
		}
	}

	private synchronized MyRuntime selectCurrentRuntime(MyRuntime rt) {
		MyRuntime old = this.m_current_runtime;
		this.m_current_runtime = rt;
		return old;
	}

	private static class MyTools {

		public static void sleep(int ms) {
			try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private class MyRuntimeContext {

		public ComPortAgent agent;
		public ComPort com;
		public InputStream in;
		public OutputStream out;
		public String com_port_name;
		public CommandBuffer tx_buffer;
		public Map<String, CommandLooper> tx_loopers = new Hashtable<String, CommandLooper>();

	}

	private class MyRuntime {

		private boolean m_do_close;
		private MyRuntimeContext m_context;

		public void run() {

			List<Thread> threads = new ArrayList<Thread>();
			MyRuntimeContext context = new MyRuntimeContext();

			try {

				System.out.println(this + ".begin()");

				context.com_port_name = Monitor.this.comPortName;
				context.agent = Monitor.this.m_agent;
				context.com = context.agent.open(context.com_port_name);
				context.in = context.com.getInput();
				context.out = context.com.getOutput();
				context.tx_buffer = new CommandBuffer();

				threads.add(new Thread(new MyTxRunnable(context)));
				threads.add(new Thread(new MyRxRunnable(context)));

				for (Thread thread : threads) {
					thread.start();
				}

				this.m_context = context;

				for (;;) {
					// wait for close
					if (this.m_do_close) {
						break;
					} else {
						MyTools.sleep(500);
					}
				}

				for (Thread thread : threads) {
					thread.interrupt();
				}

				for (Thread thread : threads) {
					thread.join();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();

			} finally {
				IOTools.close(context.in);
				IOTools.close(context.out);
				context.agent.close(context.com);
				System.out.println(this + ".end()");
			}

		}

		public void open() {
		}

		public void close() {
			this.m_do_close = true;
		}
	}

	private class MyRxRunnable implements Runnable {

		private final MyRuntimeContext m_context;

		public MyRxRunnable(MyRuntimeContext context) {
			this.m_context = context;
		}

		@Override
		public void run() {
			try {
				System.out.println(this + ".begin()");
				ProtocolHandler h = new MyProtocolHandler(this.m_context);
				ProtocolParser parser = new ProtocolParser();
				parser.parse(this.m_context.in, h);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(this + ".end()");
			}
		}
	}

	private class MyTxRunnable implements Runnable {

		private final MyRuntimeContext m_context;

		public MyTxRunnable(MyRuntimeContext context) {
			this.m_context = context;
		}

		@Override
		public void run() {
			Writer writer = null;
			try {
				MyRuntimeContext context = this.m_context;
				OutputStream out = context.out;
				writer = new OutputStreamWriter(out, "UTF-8");
				CommandBuffer buffer = context.tx_buffer;
				for (;;) {
					this.doLoopCommands();
					String cmd = buffer.pop();
					if (cmd == null) {
						Thread.sleep(200);
						continue;
					}
					Monitor.this.logcmd(cmd);
					writer.write(cmd);
					writer.write("\r\n");
					writer.flush();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOTools.close(writer);
			}
		}

		private void doLoopCommands() {
			final MyRuntimeContext context = this.m_context;
			final long now = System.currentTimeMillis();
			Map<String, CommandLooper> table = context.tx_loopers;
			Collection<CommandLooper> cmds = table.values();
			for (CommandLooper cl : cmds) {
				long next = cl.getNextTriggerTime();
				if (next <= now) {
					// trigger
					cl.setNextTriggerTime(next + cl.getInterval());
					String cmd = cl.getCommand();
					context.tx_buffer.push(cmd);
				}
			}
		}
	}

	private class MyProtocolHandler implements ProtocolHandler {

		private MonitorState m_state;
		private Map<String, StatusField> m_table;

		public MyProtocolHandler(MyRuntimeContext context) {

			MonitorState state = new MonitorState();
			Map<String, StatusField> table = new Hashtable<String, StatusField>();
			state.setFields(table);
			this.m_state = state;
			this.m_table = table;

		}

		@Override
		public void on_ok() {
			Monitor.this.logcmd("OK");
		}

		@Override
		public void on_status(long id, BatteryStatus status) {

			if (status != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("$").append(Long.toHexString(id));
				for (StatusField field : status.getFields()) {
					sb.append(field.getName()).append("=");
					sb.append(field.getValue()).append(" ");
					sb.append(field.getUnit()).append(", ");
				}
				Monitor.this.logcmd(sb.toString());

				List<StatusField> fields = status.getFields();

				Map<String, StatusField> table = this.m_table;

				for (StatusField field : fields) {
					String key = field.getName();
					table.put(key, field);
				}

				double u = this.getValue(table, "VOL");
				double i = this.getValue(table, "CUR");
				i = Math.max(Math.abs(i), 0.0000000001);

				fields.clear();
				fields.add(new StatusField("ID", 0, "" + id));
				fields.add(new StatusField("zgl", u * i, "W"));
				fields.add(new StatusField("zdz", u / i, "Ohm"));

				for (StatusField field : fields) {
					String key = field.getName();
					table.put(key, field);
				}

				this.m_state.setId(id);
				Monitor.this.setState(this.m_state);
			}

		}

		private double getValue(Map<String, StatusField> table, String key) {
			StatusField field = table.get(key);
			if (field == null) {
				return 0;
			} else {
				return field.getValue();
			}
		}
	}

	public boolean isStateOn() {
		MyRuntime rt = this.m_current_runtime;
		return (rt != null);
	}

	public synchronized void logcmd(String cmd) {
		System.out.println(cmd);
		Writer out = this.m_log_out;
		if (out == null) {
			return;
		}
		try {
			String s = cmd + "\r\n";
			out.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MonitorState getState() {
		return state;
	}

	public void setState(MonitorState state) {
		this.state = state;

		Map<String, StatusField> table = state.getFields();

		AlarmTrigger at_qy = new QYAlarmTrigger();
		AlarmTrigger at_dl = new DLAlarmTrigger();

		if (at_qy.hit(table)) {
			this.setAlarmBatLowClose(state.makeCopy());
		}

		if (at_dl.hit(table)) {
			this.setAlarmBatLow(state.makeCopy());
		}

	}

	public void send(String string) {
		MyRuntime rt = this.m_current_runtime;
		if (rt == null) {
			return;
		}
		MyRuntimeContext context = rt.m_context;
		if (context == null) {
			return;
		}
		CommandBuffer buffer = context.tx_buffer;
		buffer.push(string);
	}

	/***
	 * send command in loop mode
	 * 
	 * @param delay
	 *            ( in millisecond)
	 * @param interval
	 *            ( in millisecond)
	 */

	public void send(String cmd, int delay, int interval) {
		MyRuntime rt = this.m_current_runtime;
		MyRuntimeContext context = rt.m_context;
		if (interval > 0) {
			CommandLooper looper = new CommandLooper(cmd);
			looper.setDelay(delay);
			looper.setInterval(interval);
			looper.setNextTriggerTime(System.currentTimeMillis() + delay);
			context.tx_loopers.put(cmd, looper);
		} else {
			context.tx_loopers.remove(cmd);
		}
	}

	public void setLogOut(Writer out) {
		this.m_log_out = out;
	}
}
