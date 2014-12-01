package cn.ac.rcpa.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Calculator {

  public static String calculateFileMD5(String fileName) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");

      FileInputStream fis = new FileInputStream(fileName);
      BufferedInputStream bis = new BufferedInputStream(fis);
      int len = bis.available();

      for (int i = 0; i <= len - 1; i++) {
        int temp = bis.read();
        md.update((byte) temp);
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    return getMD5StringFromMessageDigest(md);
  }

  public static String calculateStringMD5(String str) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }

    for (int i = 0; i < str.length(); i++) {
      int temp = str.charAt(i);
      md.update((byte) temp);
    }

    return getMD5StringFromMessageDigest(md);
  }

  private static String getMD5StringFromMessageDigest(MessageDigest md) {
    byte[] byteResult = md.digest();

    String raw = "";
    String tmp = "";

    for (int i = 0; i < byteResult.length; i++) {
      tmp = Integer.toHexString(byteResult[i] & 0XFF);
      if (tmp.length() == 1) {
        raw = raw + "0" + tmp;
      } else {
        raw = raw + tmp;
      }
    }

    return raw.toUpperCase();
  }

}
