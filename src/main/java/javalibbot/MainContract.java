package javalibbot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public interface MainContract {

  //class with Bot
  interface Bot {

    List<Update> getUpdates();

    //void sendMessage(ViewMessages message);

    void startUpdate(int delayMillis);

    //messages
    void sendTextMessage(long chatId, String text);
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

