package javalibbot.Presenter;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.Bot;
import javalibbot.MainContract.DBHandler;
import javalibbot.TempBD;
import javalibbot.config.ConfigContainer;

public class TempClass implements MainContract.ProcessQuery {

  DBHandler db = new TempBD();

  @Override
  public void startQueryProcess(final Bot bot) {
    Thread th = new Thread(() -> {
      while (true) {
        List<Update> update = bot.getUpdates();
        //Обработка ответа
        for (Update up : update) {

          //CallBackQuery
          if (isCallBackQuery(up)) {
            System.out.println("this is CallBackQuery!");
            continue;
          }

          //TextMessage
          if (isTextMessage(up)) {
            System.out.println("this is TextMessage");

            //example send message
            String text = up.message().text();
            long chatId = up.message().chat().id();
            if (text == null) {
              return;
            }

            if (text.equals("/start")) {
              bot.sendStartMessage(chatId);
            } else if (text.contains(ConfigContainer.getDownloadRequest())) {
              System.out.println("!!!" + text);
              String stringId = text.trim()
                  .substring(ConfigContainer.getDownloadRequest().length());
              int id = parseStringInInteger(stringId);
              if (id != -1) {
                bot.sendSingleSearchResult(chatId, db.getBookById(id));
              }
            } else {

              bot.sendListSearchResult(chatId, db.search(text));
            }
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

  private boolean isCallBackQuery(Update update) {
    return update.callbackQuery() != null;
  }

  private boolean isTextMessage(Update update) {
    return update.message() != null;
  }

  private int parseStringInInteger(String str) {
    int result;
    try {
      result = Integer.parseInt(str.trim());
    } catch (NumberFormatException e) {
      return -1;
    }
    return result;
  }
}
