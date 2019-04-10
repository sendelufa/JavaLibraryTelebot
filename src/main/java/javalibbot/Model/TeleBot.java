package javalibbot.Model;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.*;
import java.util.ArrayList;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.ViewMessages;

public class TeleBot extends TelegramBot implements MainContract.Bot {

  //cache of tasks
  private List<Update> updateTask = new ArrayList<>();

  public TeleBot(String Token) {
    super(Token);
  }

  @Override
  public synchronized List<Update> getUpdates() {
    System.out.println("getUpdates from Bot to Presenter");
    List<Update> ret = new ArrayList<>();
    ret.addAll(updateTask);
    //clear cache of tasks
    updateTask = new ArrayList<>();
    return ret;
  }

  @Override
  public void sendMessage(ViewMessages message) {
    this.execute(message.text(""));
  }

  @Override
  public void startUpdate(int delayMillis) {
    Thread th = new Thread(() -> {
      //index of last get message from bot
      int m = 0;
      while (true) {
        List<Update> update = this.execute(new GetUpdates().limit(100).offset(m)).updates();
        updateTask.addAll(update);

      if (!update.isEmpty()) {
        m = update.get(update.size() - 1).updateId() + 1;
      }

        try {
          Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    th.start();
  }
}
