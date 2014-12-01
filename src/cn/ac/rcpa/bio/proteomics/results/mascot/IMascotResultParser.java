package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.File;

public interface IMascotResultParser {
	MascotResult parseContent(String filecontent) throws Exception;

	MascotResult parseFile(File file) throws Exception;
}
