package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ac.rcpa.bio.processor.IFileProcessor;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class SequestSpectraImageBuilder implements IFileProcessor {
	private String targetDirectory;

	private List<String> params = new ArrayList<String>();

	private IdentifiedPeptideResultImageBuilder builder;

	private ImageType drawImageType;

	public SequestSpectraImageBuilder(String aTargetDirectory, int scale,
			double peakTolerance, double precursorNeutralLossMinIntensityScale,
			double byNeutralLossMinIntensityScale, double byMinIntensityScale,
			boolean drawRemovePrecursorNeutralLossImage, ImageType drawImageType) {
		this.targetDirectory = aTargetDirectory;
		this.builder = new IdentifiedPeptideResultImageBuilder(scale,
				peakTolerance, precursorNeutralLossMinIntensityScale,
				byNeutralLossMinIntensityScale, byMinIntensityScale,
				drawRemovePrecursorNeutralLossImage, drawImageType);
		this.drawImageType = drawImageType;

		params.add("Scale = " + scale);
		params.add("Peak Tolerance = " + peakTolerance);
		params.add("Precursor Neutral Loss Ion Min Intensity Scale = "
				+ precursorNeutralLossMinIntensityScale);
		params.add("B/Y Neutral Loss Ion Min Intensity Scale = "
				+ byNeutralLossMinIntensityScale);
		params.add("B/Y Ion Min Intensity Scale = " + byMinIntensityScale);
		params.add("Draw Remove Precursor NeutralLoss Image = "
				+ drawRemovePrecursorNeutralLossImage);
		params.add("Draw format = " + drawImageType.toString());
	}

	public List<String> process(String directory) throws Exception {
		PrintWriter paramFile = new PrintWriter(targetDirectory
				+ "//ImageBuilder.params");
		try {
			for (String line : params) {
				paramFile.println(line);
			}
		} finally {
			paramFile.close();
		}

		List<File> dirs = new ArrayList<File>();
		dirs.add(new File(directory));
		dirs.addAll(Arrays.asList(RcpaFileUtils.getSubDirectories(new File(
				directory))));

		List<File> dtaFiles = new ArrayList<File>();
		for (File dir : dirs) {
			File[] curDtaFiles = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".dta");
				}
			});
			dtaFiles.addAll(Arrays.asList(curDtaFiles));
		}

		for (File dtaFile : dtaFiles) {
			String outFile = RcpaFileUtils.changeExtension(dtaFile
					.getAbsolutePath(), ".out");
			if (!new File(outFile).exists()) {
				throw new IllegalStateException(
						"Cannot find sequest out file :" + outFile);
			}

			SequestPeptideResult sr = new SequestPeptideResult();
			sr.parseFromFile(dtaFile.getAbsolutePath(), outFile);

			if (sr.getPeptide().length() == 0) {
				System.err.println("There is no peptide information in "
						+ outFile);
				continue;
			}

			String imageFilename = this.targetDirectory
					+ "/"
					+ RcpaFileUtils.changeExtension(dtaFile.getName(), "."
							+ this.drawImageType.toString());

			builder.drawImage(imageFilename, sr);
		}
		return new ArrayList<String>();
	}

}
