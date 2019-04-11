package javalibbot.Model;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import java.util.ArrayList;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.LogAction;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;

public class TeleBot extends TelegramBot implements MainContract.Bot {

  //cache of tasks
  private List<Update> updateTask = new ArrayList<>();
  private int delay = 500;

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
  public void startUpdate(int delayMillis) throws ShortDelayTimeBetweenUpdates {
    delay = delayMillis;
    if (delay < 500){
      delay = 500;
      throw new ShortDelayTimeBetweenUpdates();
    }

    Thread th = new Thread(() -> {
      //index of last get message from bot
      int m = 0;
      while (true) {
        System.out.println("get Update from API");
        List<Update> update = this.execute(new GetUpdates().limit(100).offset(m)).updates();
        updateTask.addAll(update);
        System.out.println("update size:" + update.size());
        if (!update.isEmpty()) {
          m = update.get(update.size() - 1).updateId() + 1;
        }

        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    th.start();
  }

  @Override
  public void sendTextMessage(long chatId, String text) {
    this.execute(new SendMessage(chatId, text));
  }

  //messages
  @Override
  public void sendSingleSearchResult(long chatId, String[] answer) {
    if (answer.length < 4){
      LogAction.warn("Массив меньше 4 элементов! отказ в отправке сообщения");
      return;
    }
    String title, author, description, url;
      title = answer[0];
      author = answer[1];
      description = answer[2];
      url = answer[3];


    title =
        "<b>" + title
            + "</b>\n<i>" + author + "</i>\n" + description + "\n\n <a href=\"" + url
            + "\">Скачать</a><a href=\"https://chubarov.if.ua/images/book_design_2.jpg?crc=502489758\"> </a>";
    System.out.println(title);
    this.execute(new SendMessage(chatId, title)
        .parseMode(ParseMode.HTML)
        .replyMarkup(getInlineKeyboardDownload(url)));
  }

  private InlineKeyboardMarkup getInlineKeyboardDownload(String link) {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[]{
        new InlineKeyboardButton("Скачать").url(link)
    });
    return inlineKeyboard;
  }

  public int getDelay() {
    return delay;
  }
}
