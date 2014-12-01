package cn.ac.rcpa.bio.sequest;

import java.io.IOException;

public interface ISequestParamsWriter {
	void saveToFile(String filename, SequestParams sp) throws IOException;
}
