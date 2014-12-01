package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.IMergedFileIterator;
import cn.ac.rcpa.utils.Pair;

public abstract class AbstractMergedFileIterator implements IMergedFileIterator {
	private BufferedReader reader;

	private boolean bHasNext;

	private boolean bContainSplitLine;
	
	public AbstractMergedFileIterator(BufferedReader br, boolean containSplitLine)
			throws IOException {
		super();
		this.reader = br;
		this.bContainSplitLine = containSplitLine;
		this.bHasNext = doHasNext();
	}

	protected boolean doHasNext() throws IOException {
		reader.mark(100000);
		try {
			String line;

			while ((line = reader.readLine()) != null) {
				if (isNextFileStart(line)) {
					return true;
				}
			}

			return false;
		} finally {
			reader.reset();
		}
	}

	protected abstract boolean isNextFileStart(String line);

	public abstract String getFormatName();

	public boolean hasNext() {
		return bHasNext;
	}

	public Pair<String, String[]> next() throws IOException {
		if (!hasNext()) {
			throw new IllegalThreadStateException(
					"No file left, call hasNext() first!");
		}

		Pair<String, String[]> result = new Pair<String, String[]>();
		List<String> content = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			if (isNextFileStart(line)) {
				result.fst = line.trim();
				if (bContainSplitLine) {
					content.add(line);
				}
				break;
			}
		}

		bHasNext = false;
		while (true) {
			reader.mark(100000);
			line = reader.readLine();
			if (line == null) {
				break;
			}

			if (isNextFileStart(line)) {
				bHasNext = true;
				reader.reset();
				break;
			}

			if(line.trim().length() == 0){
				continue;
			}
			
			content.add(line);
		}

		result.snd = content.toArray(new String[0]);
		return result;
	}

}
