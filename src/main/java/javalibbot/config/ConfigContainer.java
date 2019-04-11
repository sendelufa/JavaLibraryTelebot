package javalibbot.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigContainer {

  private static Properties props = new Properties();

  private static String TOKEN, DOWNLOAD_REQUEST;
  private static int DELAY_UPDATE;

  public static void read() {
    try {
      props.load(new InputStreamReader(new FileInputStream(new File("res/config.ini")),
          StandardCharsets.UTF_8));
    }
    catch (FileNotFoundException e){
      System.out.println("File not found");
      e.printStackTrace();
    }
    catch (IOException e){
      System.out.println("IOException at read config");
      e.printStackTrace();
    }

    //TL Connect settings
    TOKEN = props.getProperty("TOKEN");
    DELAY_UPDATE = Integer.parseInt(props.getProperty("DELAY_UPDATE"));
    DOWNLOAD_REQUEST = props.getProperty("DOWNLOAD_REQUEST");

  }

  public static String getToken() {
    return TOKEN;
  }

  public static int getDelayUpdate() {
    return DELAY_UPDATE;
  }

  public static String getDownloadRequest() {
    return DOWNLOAD_REQUEST;
  }
}
