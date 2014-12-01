package cn.ac.rcpa.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.jdom.JDOMException;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class TestXMLFile extends TestCase {
  private File tempFile= new File("data/testxmlfile.xml");
  private XMLFile xMLFile = null;
  

  protected void setUp() throws Exception {
    super.setUp();
    xMLFile = new XMLFile(tempFile.getAbsolutePath());
  }

  protected void tearDown() throws Exception {
    xMLFile = null;
    tempFile.delete();
    super.tearDown();
  }

  public void testSetElementValues() throws JDOMException, FileNotFoundException, IOException {
    String[] expect = new String[]{"test1","test2","test3"};
    xMLFile.setElementValues("","testValue", expect);
    xMLFile.setElementValues("testValues","testValue",expect);
    String[] actual = xMLFile.getElementValues("","testValue");
    assertEquals(Arrays.asList( expect), Arrays.asList( actual));
    actual = xMLFile.getElementValues("testValues","testValue");
    assertEquals(Arrays.asList( expect), Arrays.asList( actual));
  }

  public void testSetElementMap() throws Exception {
    Map<String, String> expect = new HashMap<String, String>();
    expect.put("1","test1");
    expect.put("2","test2");
    expect.put("3","test3");
    
    xMLFile.setElementMap("","Annotation", expect);
    Map<String, String> actual = xMLFile.getElementMap("","Annotation");
    assertEquals(expect, actual);

    xMLFile.setElementMap("Annotations","Annotation", expect);
    actual = xMLFile.getElementMap("Annotations","Annotation");
    assertEquals(expect, actual);
  }

}
