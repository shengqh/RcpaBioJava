/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */

package org.biojava.bio.seq.io;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.biojava.bio.BioException;
import org.biojava.bio.alignment.AlignmentElement;
import org.biojava.bio.alignment.FlexibleAlignment;
import org.biojava.bio.alignment.SimpleAlignmentElement;
import org.biojava.bio.seq.ProteinTools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.symbol.Alignment;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.bio.symbol.LocationTools;

/**
 * This class implements the AlignmentFormat interface to read Clustal alignments.
 * It is modeled after the ClustalAlignmentFormat class.
 *
 * @author Sheng Quanhu
 */

public class ClustalAlignmentFormat
    implements AlignmentFormat {
  //Constants
  public static final int DNA = 1;
  public static final int PROTEIN = 2;

  private static Pattern pattern = null;
  private static Pattern getPattern(){
    if (pattern == null){
      pattern = Pattern.compile("^(\\S+)\\s+(\\S+)");
    }
    return pattern;
  }

  public ClustalAlignmentFormat() {
  }

  /**
   * Reads an alignment in Clustal format.
   */
  public Alignment read(BufferedReader br) {
    try {
      String line = br.readLine();

      List<Sequence> sequences = new ArrayList<Sequence>();
      while((line = br.readLine()) != null){
        Matcher matcher = getPattern().matcher(line);
        if (matcher.find()){
          String name = matcher.group(1);
          String sequence = matcher.group(2);
          System.out.println(line);

          int ifind = -1;
          for(int i = 0;i < sequences.size();i++){
            if (((Sequence)sequences.get(i)).getName().equals(name)){
              ifind = i;
              break;
            }
          }
          if (ifind == -1){
            sequences.add(ProteinTools.createGappedProteinSequence(sequence, name));
          }
          else{
            String mergeSequence = ((Sequence)sequences.get(ifind)).seqString() + sequence;
            sequences.set(ifind, ProteinTools.createGappedProteinSequence(mergeSequence, name));
          }
        }
      }

      List<AlignmentElement> aes = new ArrayList<AlignmentElement>();
      for(int i = 0;i < sequences.size();i++){
        Sequence seq = sequences.get(i);
        AlignmentElement ae = new SimpleAlignmentElement(seq.getName(), seq, LocationTools.makeLocation(1, seq.seqString().length()));
        aes.add(ae);
      }
      return new FlexibleAlignment(aes) ;
    }
    catch (Exception e) {
      System.err.println("ClustalAlignmentFormat.read -- " + e.getMessage());
    }
    return null;
  }

  /**
   * Writes out the alignment to an Clustal file.
   */
  public void write(OutputStream os, Alignment align, int fileType) throws
      BioException, IllegalSymbolException {
  } //end write

  public void writeDna(OutputStream os, Alignment align) throws BioException,
      IllegalSymbolException {
    write(os, align, DNA);
  }

  public void writeProtein(OutputStream os, Alignment align) throws
      BioException, IllegalSymbolException {
    write(os, align, PROTEIN);
  }
}
