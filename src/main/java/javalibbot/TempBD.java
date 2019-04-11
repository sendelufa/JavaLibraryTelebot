package javalibbot;

import java.util.ArrayList;
import java.util.List;
import javalibbot.MainContract.DBHandler;

public class TempBD implements MainContract.DBHandler {

  @Override
  public DBHandler getInstance() {
    return null;
  }

  @Override
  public void execute(String SQLQuery) {

  }

  @Override
  public List<String[]> search(String searchText) {
    // String[] = {Id, BookTitle, Author}
    ArrayList<String[]> searchResult = new ArrayList<>();
    for (Integer i = 0; i < 5; i++){
      String[] answer = {i.toString(), "Книга " + i, "Автор книги " + i};
      searchResult.add(answer);
    }

    return searchResult;
  }

  // String[] {Id, BookTitle, Author, Description, download_url, img_url}
  @Override
  public String[] getBookById(int id) {
    String[] answer = {String.valueOf(id), "Книга", "Автор книги", "Описание", "http://sendel.ru", "http://sendel.ru/wp-content/uploads/2012/10/1280px-05684-21-190x160.jpg"};
    return answer;
  }
}
