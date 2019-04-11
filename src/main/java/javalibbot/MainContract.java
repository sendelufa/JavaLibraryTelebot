package javalibbot;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import java.util.logging.Logger;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;

public interface MainContract {

  //class with Bot
  interface Bot {

    List<Update> getUpdates();

    void startUpdate(int delayMillis) throws ShortDelayTimeBetweenUpdates;

    //messages
    void sendTextMessage(long chatId, String text);

    void sendSingleSearchResult(long chatId, String[] answer);

    int getDelay();
  }

  //get data from db
  interface DBHandler {

    DBHandler getInstance();

    void execute(String SQLQuery);

    List<String[]> search(String searchText);

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

