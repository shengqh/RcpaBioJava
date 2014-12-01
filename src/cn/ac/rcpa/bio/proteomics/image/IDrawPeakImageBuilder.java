package cn.ac.rcpa.bio.proteomics.image;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface IDrawPeakImageBuilder {
	/**
	 * draw image to output stream
	 * @param os
	 * @param peaks
	 * @throws IOException
	 */
	void draw(OutputStream os, Dimension dim, List<IDrawPeak> peaks) throws IOException;
}
