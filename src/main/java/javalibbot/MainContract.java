package javalibbot;

import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface MainContract {

  //class with Bot
  interface Bot {

    List<Update> getUpdates();

    void startUpdate(int delayMillis);

    //messages
    void sendTextMessage(long chatId, String text);

    void sendSingleSearchResult(long chatId, String[] answer);
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


}

