package cn.ac.rcpa.tools.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GetFirstNLinesFromHugeFile {
  private GetFirstNLinesFromHugeFile() {
  }

  public static void getFirstNLine(File sourceFile, File targetFile, int lineCount) throws IOException {
    PrintWriter pw = new PrintWriter(new FileWriter(targetFile));
    BufferedReader br = new BufferedReader(new FileReader(sourceFile));

    int iCount = 0;
    String line;
    while((line = br.readLine()) != null){
      pw.println(line);
      iCount ++;
      if (iCount >= lineCount){
        break;
      }
    }
    pw.close();
  }

/*  public static void main(String[] args) throws IOException {
//    getFirstNLine("D:\\database\\annotation\\sptr.goa", "D:\\database\\annotation\\sptr_tmp.goa", 10);
    getFirstNLine("D:\\database\\ipi.RAT.dat", "D:\\database\\annotation\\sptr_tmp.goa", 10);
  }
*/
}
