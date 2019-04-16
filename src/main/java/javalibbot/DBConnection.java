package javalibbot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static Connection connection;

  private static StringBuilder sb = new StringBuilder();
  private static int bufferSize = 100_000;
  private static String dbName = "learn";
  private static String dbUser = "root";
  private static String dbPass = "qwe123";

  private static int num = 0;
  private static long numTime = System.currentTimeMillis();
  private static long numTime2 = numTime;

  public static Connection getConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/" + dbName +
                "?user=" + dbUser + "&password=" + dbPass);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }
}
