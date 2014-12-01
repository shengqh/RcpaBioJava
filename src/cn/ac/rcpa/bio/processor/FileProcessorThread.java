package cn.ac.rcpa.bio.processor;

import java.io.File;
import java.util.List;

import cn.ac.rcpa.models.IInterruptable;
import cn.ac.rcpa.models.MessageType;

public class FileProcessorThread extends Thread {
	private IFileProcessor processor;

	private String originFilename;

	private IThreadCaller caller;

	public FileProcessorThread(IFileProcessor processor, String originFilename,
			IThreadCaller caller) {
		this.processor = processor;
		this.originFilename = originFilename;
		this.caller = caller;
	}

	private synchronized void callThreadStarted() {
		caller.threadStarted(this);
	}

	private synchronized void callThreadFinished() {
		caller.threadFinished(this);
	}

	@Override
	public void run() {
		try {
			callThreadStarted();

			List<String> resultFiles = processor.process(originFilename);
			if (interrupted()) {
				return;
			}

			boolean bIsFile = true;
			StringBuffer sb = new StringBuffer();
			for (String file : resultFiles) {
				sb.append("\n" + file);
				if (!new File(file).exists()) {
					bIsFile = false;
				}
			}

			callThreadFinished();

			if (0 == resultFiles.size()) {
				caller.showMessage(MessageType.INFO_MESSAGE, "Task finished.");
			} else if (bIsFile) {
				caller.showMessage(MessageType.INFO_MESSAGE, "Result has saved to : "
						+ sb.toString());
			} else {
				caller.showMessage(MessageType.INFO_MESSAGE, sb.toString());
			}
		} catch (Exception ex) {
			callThreadFinished();
			ex.printStackTrace();
			caller.showMessage(MessageType.ERROR_MESSAGE, ex.getMessage());
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
		if (processor instanceof IInterruptable) {
			((IInterruptable) processor).interrupt();
		}
	}
}
