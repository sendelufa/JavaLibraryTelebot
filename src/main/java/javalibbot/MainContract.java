package javalibbot;

import com.pengrad.telegrambot.model.Update;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;

public interface MainContract {

  //class with Bot
  interface Bot {

    List<Update> getUpdates();

    void startUpdate(int delayMillis) throws ShortDelayTimeBetweenUpdates;

    int getDelay();

    //messages
    void sendTextMessage(long chatId, String text);

    void sendSingleSearchResult(long chatId, String[] answer);

    void sendStartMessage(long chatId);

    void sendHelpMessage(long chatId);

    void sendListSearchResult(long chatId, List<String[]> list);

    void sendEmptySearchResult(long chatId);
  }

  //get data from db
  interface DBHandler {

    DBHandler instance  = LibraryDB.getInstance();

    ResultSet execute(String SQLQuery) throws SQLException;

    // String[] = {Id, BookTitle, Author}
    List<String[]> search(String searchText) throws SQLException;

    void addBook(String[] s) throws SQLException;

    // String[] {Id, BookTitle, Author, Description, download_url, img_url}
    String[] getBookById(int id) throws SQLException;



  }

  //processing requests from users
  interface ProcessQuery {

    void startQueryProcess(Bot bot);
  }

  interface LogAction{
    Logger l = Logger.getLogger("1");

    static logPrint warn(String text){
      l.warning(text);
      return new logPrint(text);
    }

    static String info(String text){
      l.info(text);
      return text;
    }

    static logPrint severe(String text){
      l.severe(text);
      return new logPrint(text);
    }

    class logPrint{
      String text;
      logPrint(String text){
        this.text = text;
      }

      public void writeToFile(){
        System.out.println("write to file" + text);
      }
    }
  }




}

