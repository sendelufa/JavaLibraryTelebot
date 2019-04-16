package javalibbot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibraryDB implements MainContract.DBHandler {
  private static LibraryDB instanceOne;
  private Connection connection;
  private Statement st;

  public static MainContract.DBHandler getInstance() {
    if(instanceOne == null) {
      try {
        instanceOne = new LibraryDB();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return instanceOne;
  }

  private LibraryDB() throws SQLException {
    connection = DBConnection.getConnection();
      st = connection.createStatement();
  }

  public ResultSet execute(String SQLQuery){
    try {
      st.execute(SQLQuery);
      return st.getResultSet();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String[] getBookById(int id){
      return search("", "id").get(0);
  }

  @Override
  public List<String[]> search(String searchText){
    return search(searchText, "title");
  }

  private List<String[]> search(String searchText, String column){
    String sql = "SELECT * FROM learn.books WHERE " + column + " LIKE ('%" + searchText + "%')";
    ResultSet set = execute(sql);
    ArrayList<String[]> list = new ArrayList<>();
    try {
      while (set.next()) {
//      String[] {Id, BookTitle, Author, Description, download_url, img_url}
        String[] s = new String[6];
        for (int i = 0; i < 6; i++) {
          s[i] = set.getString(i + 1);
        }
        list.add(s);
      }
    } catch (SQLException e){e.printStackTrace();}

    return list;
  }

  @Override
  public void addBook(String[] s){
    String sql = "INSERT INTO books (`title`, `author`, `descr`, `download_url`, `img_url`) " +
        "VALUES('" + s[1] + "','" + s[2] + "','" + s[3] + "','" + s[4] + "','" + s[5] + "')";
    execute(sql);
  }
}
