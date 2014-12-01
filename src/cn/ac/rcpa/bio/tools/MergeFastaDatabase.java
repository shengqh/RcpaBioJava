/*
 * Created on 2005-1-20
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojava.utils.ChangeVetoException;

import cn.ac.rcpa.bio.utils.SequenceUtils;

/**
 * @author sqh
 *
 */
public class MergeFastaDatabase {
  
  public static void main(String[] args) throws NoSuchElementException, BioException, IOException, IllegalArgumentException, ChangeVetoException {
    if (args.length < 3){
      System.err.println("MergeFastaDatabase source_fasta1 source_fasta2 ... target_fasta");
      return;
    }
    
    ArrayList<Sequence> totalSequences = new ArrayList<Sequence>();
    
    for(int i = 0;i < args.length - 1;i++){
      totalSequences.addAll(SequenceUtils.readFastaProteins(new File(args[i])));
    }
    
    for(Sequence seq:totalSequences){
      String description = (String)seq.getAnnotation().getProperty("description");
      String description_line = (String)seq.getAnnotation().getProperty("description_line");
      
      description = description.replace('\t',' ');
      description_line = description_line.replace('\t',' ');
      
      seq.getAnnotation().setProperty("description", description);
      seq.getAnnotation().setProperty("description_line", description_line);
    }
    
    SequenceUtils.writeFasta(new File(args[args.length - 1]), totalSequences);
  }

}
