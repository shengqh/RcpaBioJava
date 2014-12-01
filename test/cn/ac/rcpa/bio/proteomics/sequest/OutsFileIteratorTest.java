package cn.ac.rcpa.bio.proteomics.sequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.ac.rcpa.utils.Pair;

import junit.framework.TestCase;

public class OutsFileIteratorTest extends TestCase {

	/*
	 * Test method for 'cn.ac.rcpa.bio.proteomics.sequest.OutsFileIterator.next()'
	 */
	public void testNext() throws IOException {
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(
				"data/20030428_4_29L_15.outs"));
		try {
			OutsFileIterator iter = new OutsFileIterator(br);
			Pair<String, String[]> file = null;
			while (iter.hasNext()) {
				count++;
				file = iter.next();
			}
			assertEquals(731, count);
			assertNotNull(file);
			assertEquals("20030428_4_29L_15.2995.2995.1.out", file.fst);
			assertEquals(" 20030428_4_29L_15.2995.2995.1.out", file.snd[0]);
		} finally {
			br.close();
		}
	}

}
