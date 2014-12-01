package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.sequest.SequestParseException;
import cn.ac.rcpa.bio.utils.SequenceUtils;

public class BioworkExcelReader
    implements IIdentifiedResultReader<BuildSummaryResult> {
  private static BioworkExcelReader instance = null;

  private BioworkExcelReader() {
  }

  public static BioworkExcelReader getInstance() {
    if (instance == null) {
      instance = new BioworkExcelReader();
    }
    return instance;
  }

  public BuildSummaryResult read(String filename) throws IOException {
    BuildSummaryResult result = new BuildSummaryResult();

    ArrayList<BuildSummaryProtein> proteins = new ArrayList<BuildSummaryProtein> ();

    HSSFSheet sheet = getFirstSheet(filename);
    int iBeginPos = -1;
    while (true) {
      iBeginPos = getNextProteinEntryIndex(sheet, iBeginPos + 1);
      if (iBeginPos >= sheet.getLastRowNum()) {
        break;
      }
      proteins.add(getNextProteinHit(sheet, iBeginPos));
    }

    BuildSummaryProteinGroup group = new BuildSummaryProteinGroup();
    for (int i = 0; i < proteins.size(); i++) {
      if (group.getProteinCount() == 0) {
        group.addProtein(proteins.get(i));
        result.addProteinGroup(group);
      }
      else {
        Set<String>
            oldUniquePeps = new HashSet<String> (Arrays.asList(group.getProtein(
                0).getUniquePeptides()));
        Set<String>
            curUniquePeps = new HashSet<String> (Arrays.asList(proteins.get(i).
            getUniquePeptides()));
        if (oldUniquePeps.equals(curUniquePeps)) {
          group.addProtein(proteins.get(i));
        }
        else {
          group = new BuildSummaryProteinGroup();
          group.addProtein(proteins.get(i));
          result.addProteinGroup(group);
        }
      }
    }

    result.buildProteinPeptideRelationship();
    result.rebuildGroupIndex();
    result.buildGroupRelationship();
    return result;
  }

  private BuildSummaryProtein getNextProteinHit(HSSFSheet sheet,
                                                int iBeginPos) {
    BuildSummaryProtein result = new BuildSummaryProtein();
    result.setReference(sheet.getRow(iBeginPos).getCell( (short) 1).
                        getStringCellValue());
    result.setProteinName(SequenceUtils.getProteinName(result.getReference()));
/*    result.setCoverage(sheet.getRow(iBeginPos).getCell( (short) 6).
                       getNumericCellValue());
    result.setMW(sheet.getRow(iBeginPos).getCell( (short) 7).
                 getNumericCellValue());
*/
    int iEndPos = getNextProteinEntryIndex(sheet, iBeginPos + 1);
    for (int i = iBeginPos + 1; i < iEndPos; i++) {
      result.addPeptide(getPeptideHit(sheet.getRow(i)));
    }

    return result;
  }

  private BuildSummaryPeptide getPeptideHit(HSSFRow row) {
    BuildSummaryPeptide result = new BuildSummaryPeptide();
    try {
      result.setFilename(row.getCell( (short) 1).getStringCellValue());
    }
    catch (SequestParseException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
    result.setSequence(row.getCell( (short) 2).getStringCellValue());
    result.setTheoreticalSingleChargeMass(row.getCell( (short) 3).getNumericCellValue());
    result.setCharge( (int) row.getCell( (short) 4).getNumericCellValue());
    result.setXcorr(row.getCell( (short) 5).getNumericCellValue());
    result.setDeltacn(row.getCell( (short) 6).getNumericCellValue());
    result.setSp(row.getCell( (short) 7).getNumericCellValue());
    result.setSpRank(new Double(row.getCell( (short) 8).getNumericCellValue()).
                     intValue());

    String ioncount = row.getCell( (short) 9).getStringCellValue();
    result.setMatchCount(Integer.parseInt(ioncount.substring(0,
        ioncount.indexOf('/'))));
    result.setTheoreticalCount(Integer.parseInt(ioncount.substring(ioncount.
        indexOf('/') + 1)));

    return result;
  }

  private int getNextProteinEntryIndex(HSSFSheet sheet, int beginPos) {
    for (int i = beginPos; i < sheet.getLastRowNum(); i++) {
      if (isProteinEntry(sheet.getRow(i).getCell( (short) 0))) {
        return i;
      }
    }
    return sheet.getLastRowNum();
  }

  private String getCellValue(HSSFCell cell) {
    switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case HSSFCell.CELL_TYPE_NUMERIC:
        return Double.toString(cell.getNumericCellValue());
      default:
        return cell.getStringCellValue();
    }
  }

  private boolean isProteinEntry(HSSFCell cell) {
    if (cell == null) {
      return false;
    }

    String value = getCellValue(cell);
    if (value == null || value.length() == 0) {
      return false;
    }

    return true;
  }

  private HSSFSheet getFirstSheet(String filename) throws IOException {
    return new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(new File(
        filename)))).getSheetAt(0);
  }
}
