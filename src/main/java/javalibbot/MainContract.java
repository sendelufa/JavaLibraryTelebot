package javalibbot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;

public interface MainContract {

  //class with Bot
  interface Bot {

    void startBot(String Token);

    List<Update> getUpdates(int limit, int offset);

    void sendMessage(ViewMessages message);
  }

  //get data from db
  interface DBHandler {

    DBHandler getInstance();

    void execute(String SQLQuery);

    List<String[]> search(String searchText);

  }

  //processing requests from users
  interface ProcessQuery {

    void startQueryProccess(Bot bot);
  }

  //processing requests from users
  interface ViewMessages {

    //standart text messages
    SendMessage text(String text);

    SendMessage startMessage();

    SendMessage helpMessage();

    //books messages
    SendMessage searchQueryText();

    SendMessage book(String[] book);

    SendMessage booksList(List<String[]> books);

  }

}

