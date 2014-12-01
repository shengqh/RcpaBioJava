package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.utils.Pair;

public class DtasFileIteratorTest extends TestCase {

	/*
	 * Test method for 'cn.ac.rcpa.bio.proteomics.sequest.DtasFileIterator.next()'
	 */
	public void testNext() throws IOException {
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(
				"data/20030428_4_29L_15.dtas"));
		try {
			DtasFileIterator iter = new DtasFileIterator(br);
			Pair<String,String[]> file = null;
			while (iter.hasNext()) {
				count++;
				file = iter.next();
			}
			assertEquals(731, count);
			assertNotNull(file);
			assertEquals("20030428_4_29L_15.2995.2995.1.dta", file.fst);
			assertEquals("464.24 1 ", file.snd[0]);
		} finally {
			br.close();
		}
	}
}
