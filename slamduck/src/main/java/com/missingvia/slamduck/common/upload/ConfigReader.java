package com.missingvia.slamduck.common.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader
{
  private static Logger logger = LoggerFactory.getLogger(ConfigReader.class);

  public static Properties loadXMLProperties(File[] files)
  {
    if ((files == null) || (files.length == 0)) {
      return null;
    }
    Properties pAll = new Properties();
    try {
      for (File file : files) {
        Properties p = loadFromURL(file.toURI().toURL());
        if (p != null) {
          pAll.putAll(p);
        }
      }
      return pAll;
    } catch (MalformedURLException e) {
      logger.error("读取配置文件" + files + "出错：" + e);
    }return null;
  }

  public static Properties loadXMLProperties(String[] locations)
  {
    if ((locations == null) || (locations.length == 0)) {
      return null;
    }
    Properties pAll = new Properties();
    for (String location : locations) {
      Properties p = loadXMLProperties(location);
      if (p != null) {
        pAll.putAll(p);
      }
    }
    return pAll;
  }

  public static Properties loadXMLProperties(String location)
  {
    Properties pAll = new Properties();
    try {
      Enumeration urls = ConfigReader.class.getClassLoader().getResources(location);
      while (urls.hasMoreElements()) {
        URL url = (URL)urls.nextElement();
        Properties p = loadFromURL(url);
        if (p != null)
          pAll.putAll(p);
      }
    }
    catch (IOException e) {
      pAll = null;
      logger.error("读取配置文件" + location + "出错：" + e);
    }
    return pAll;
  }

  private static Properties loadFromURL(URL url)
  {
    InputStream is = null;
    Properties p = new Properties();
    try {
      URLConnection con = url.openConnection();
      con.setUseCaches(false);
      is = con.getInputStream();
      p.loadFromXML(is);
    } catch (IOException e) {
      p = null;
      logger.error("读取配置文件" + url + "出错：" + e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          logger.error("读取配置文件" + url + "出错：" + e);
        }
      }
    }
    return p;
  }
}
