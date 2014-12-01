package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.io.PrintStream;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0.0
 */
public interface IPeakListWriter<T extends Peak> {
	String getFormatName();

	void write(PrintStream ps, PeakList<? extends T> peaks) throws IOException;
}
