package cn.ac.rcpa;

import java.io.IOException;

import cn.ac.rcpa.utils.Pair;

public interface IMergedFileIterator {
	String getFormatName();

	/**
	 * Has next file?
	 * 
	 * @return
	 */
	boolean hasNext();

	/**
	 * Read next file.
	 * 
	 * @return Pair<String,String[]>, first element of Pair is filename usually,
	 *         second element of Pair is file content
	 * @throws IOException
	 */
	Pair<String, String[]> next() throws IOException;
}
