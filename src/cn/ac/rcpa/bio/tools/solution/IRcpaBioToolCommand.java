package cn.ac.rcpa.bio.tools.solution;

public interface IRcpaBioToolCommand {
	String[] getMenuNames();

	String getCaption();

	String getVersion();

	void run();
}
