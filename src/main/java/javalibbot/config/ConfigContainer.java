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

  private static String TOKEN = "";

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
  }

  public static String getToken() {
    return TOKEN;
  }
}
