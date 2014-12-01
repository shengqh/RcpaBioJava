package cn.ac.rcpa.bio.sequest;

import java.io.IOException;

public interface ISequestParamsReader {
	SequestParams loadFromFile(String filename) throws IOException;
}
