/*
 * Created on 2005-12-14
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.database.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ac.rcpa.bio.database.RcpaDatabaseType;

public class ApplicationContextFactory {
  private static Map<RcpaDatabaseType, String[]> databaseMap = new HashMap<RcpaDatabaseType, String[]>();
  static {
    databaseMap.put(RcpaDatabaseType.ANNOTATION, new String[]{"annotation-applicationContext.xml","annotationSessionFactory"});
  }

  private static Map<RcpaDatabaseType, ApplicationContext> applicationMap = new HashMap<RcpaDatabaseType, ApplicationContext>();

  public static SessionFactory getSessionFactory(RcpaDatabaseType dbType) {
    String sessionFactoryName = databaseMap.get(dbType)[1];
    return (SessionFactory)getContext(dbType).getBean(sessionFactoryName);
  }
  
  public static ApplicationContext getContext(RcpaDatabaseType dbType) {
    if (applicationMap.containsKey(dbType)) {
      return applicationMap.get(dbType);
    }

    if (!databaseMap.containsKey(dbType)) {
      throw new IllegalStateException(
          "There is no applicationContext defined to " + dbType
              + ", call programmer to correct it!");
    }

    String applicationContextFile = databaseMap.get(dbType)[0];
    ApplicationContext result = new ClassPathXmlApplicationContext(
        applicationContextFile);
    applicationMap.put(dbType, result);
    return result;
  }
}
