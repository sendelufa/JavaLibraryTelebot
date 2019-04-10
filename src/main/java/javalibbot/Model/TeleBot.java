package javalibbot.Model;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import javalibbot.MainContract;
import javalibbot.MainContract.ViewMessages;
import javalibbot.Messages.TextMessage;

public class TeleBot extends TelegramBot implements MainContract.Bot {

  List<Update> updateTask = new ArrayList<>();

  public TeleBot(String Token) {
    super(Token);
  }

  @Override
  public List<Update> getUpdates(int limit, int offset) {
    System.out.println("getUpdates");
    return this.execute(new GetUpdates().limit(limit).offset(offset)).updates();
  }

  @Override
  public void sendMessage(ViewMessages message) {
    this.execute(message.text(""));
  }

  @Override
  public void startUpdate(int delayMillis) {
    Thread th = new Thread(() -> {
      int m = 0;
      while (true) {
        List<Update> update = this.getUpdates(100, m);
        for (Update up : update) {
          //сдвигаем ofsset на 1 чтобы не получать прошлые сообщения при апдейте
          m = up.updateId() + 1;

          //Обработка ответа
          String text = up.message().text();
          if (text.toLowerCase().contains("привет")){
            this.sendMessage(new TextMessage(up.message().chat().id(), "привет! каг дила?"));
          }
          else {
            this.sendMessage(new TextMessage(up.message().chat().id(), "задачи " + update.size()));
          }

        }

        try {
          Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println("getUpdate " + m);
      }
    });

    th.start();
  }
}
