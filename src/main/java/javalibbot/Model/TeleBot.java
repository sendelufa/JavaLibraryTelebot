package javalibbot.Model;

import com.pengrad.telegrambot.*;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.LogAction;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;
import javalibbot.config.ConfigContainer;

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
    if (delay < 500) {
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

  @Override
  public void sendStartMessage(long chatId) {
    this.execute(new SendMessage(chatId, "Наберите текст для поиска или выберите пункт в меню")
        .replyMarkup(getInlineKeyboardStart()));
  }

  @Override
  public void sendHelpMessage(long chatId) {

  }

  @Override
  public void sendListSearchResult(long chatId, List<String[]> list) {
    String text = "";
    if (list != null && list.size() > 0) {
      Iterator<String[]> iterator = list.iterator();

      while (iterator.hasNext()) {
        String[] book = iterator.next();
        String id = book[0];
        String title = book[1];
        String author = book[2];

        text += "\uD83D\uDCD7 <b>" + title + "</b> " + author + "\n Скачать: " + ConfigContainer.getDownloadRequest() + id;
        if (iterator.hasNext()) {
          text += "\n\n";
        }
      }
      text += "\n\n<i>Не нашли?</i> Попробуйте ввести другой запрос";

      this.execute(new SendMessage(chatId, text).parseMode(ParseMode.HTML)
          .replyMarkup(getInlineKeyboardStart()));

    } else {
      sendEmptySearchResult(chatId);
    }
  }

  @Override
  public void sendEmptySearchResult(long chatId) {
    String text = "\uD83D\uDE29 Соррян, ничего не найденно!";
    sendTextMessage(chatId, text);
  }

  //messages
  @Override
  public void sendSingleSearchResult(long chatId, String[] answer) {
    if (answer.length < 6) {
      LogAction.warn("Массив меньше 6 элементов! отказ в отправке сообщения");
      return;
    }
    String id = answer[0];
    String title = answer[1];
    String author = answer[2];
    String description = answer[3];
    String download_url = answer[4];
    String img_url = answer[5];

    String text =
        "<b>" + title
            + "</b>\n<i>" + author + "</i>\n" + description + "\n\n <a href=\"" + download_url
            + "\">Скачать</a><a href=\"" + img_url + "\"> </a>";

    System.out.println(text);

    this.execute(new SendMessage(chatId, text)
        .parseMode(ParseMode.HTML)
        .replyMarkup(getInlineKeyboardDownload(download_url)));
  }

  private InlineKeyboardMarkup getInlineKeyboardDownload(String link) {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[]{
        new InlineKeyboardButton("Скачать").url(link)
    });
    return inlineKeyboard;
  }

  private InlineKeyboardMarkup getInlineKeyboardStart() {
    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[]{
        new InlineKeyboardButton("Последние книги").callbackData("/lastbooks"),
        new InlineKeyboardButton("Популярные книги").callbackData("/popularbooks")
    });
    return inlineKeyboard;
  }

  public int getDelay() {
    return delay;
  }
}
