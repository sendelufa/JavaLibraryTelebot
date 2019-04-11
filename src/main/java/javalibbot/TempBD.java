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

    String[] answer1 = {"1", "Easy Learning Java: Java for Beginner's Guide (2019)",
        "Yang Hu"};
    String[] answer2 = {"2", "Java in Two Semesters: Featuring JavaFX, 4th edition (2019)",
        "Quentin Charatan, Aaron Kans"};
    String[] answer3 = {"3", "Java Program Design / (2019)",
        "Sciore E.",};
    String[] answer4 = {"4",
        "Java Concurrency in Practice / Параллельное программирование в JAVA на практике (2006)",
        "Брайан Готц, Тим Пайерлс, Джошуа Блох, Джозеф Боубир, Дэвид Холмс, Дуг Ли",};
    String[] answer5 = {"5", "Spring Boot 2 Recipes / (2018)",
        "Marten Deinum"};
    searchResult.add(answer1);
    searchResult.add(answer2);
    searchResult.add(answer3);
    searchResult.add(answer4);
    searchResult.add(answer5);

    return searchResult;
  }

  // String[] {Id, BookTitle, Author, Description, download_url, img_url}
  @Override
  public String[] getBookById(int id) {
    String[] answer = {String.valueOf(id), "Easy Learning Java: Java for Beginner's Guide (2019)",
        "Yang Hu",
        "Have you always wanted to learn computer programming but are afraid it'll be too difficult for you? Or perhaps you know other programming languages but are interested in learning the Java language fast?",
        "http://sendel.ru",
        "http://sendel.ru/wp-content/uploads/2012/10/1280px-05684-21-190x160.jpg"};
    return answer;
  }
}
