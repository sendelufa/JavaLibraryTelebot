package javalibbot.Presenter;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.Bot;
import javalibbot.Messages.TextMessage;

public class TempClass implements MainContract.ProcessQuery {

  @Override
  public void startQueryProcess(Bot bot) {
    Thread th = new Thread(() -> {
      while (true) {
        List<Update> update = bot.getUpdates();
        //Обработка ответа
        for (Update up : update) {

          //example send message
          String text = up.message().text();
          if (text.toLowerCase().contains("привет")) {
            bot.sendMessage(new TextMessage(up.message().chat().id(), "привет! каг дила?"));
          } else {
            bot.sendMessage(new TextMessage(up.message().chat().id(), "задачи " + update.size()));
          }
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    th.start();

  }
}
