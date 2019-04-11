package javalibbot.Presenter;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.Bot;

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
          long chatId = up.message().chat().id();
          if (text != null && text.toLowerCase().contains("привет")) {
            bot.sendTextMessage(chatId, "88");
          } else {
            String[] answer = {"Заголовок книги", "Автор книги",
                "This easy-to-follow textbook teaches Java programming from first principles, as well as covering design and testing methodologies. The text is divided into two parts. Each part supports a one-semester module, the first part addressing fundamental programming concepts, and the second part building on this foundation, teaching the skills required to develop more advanced applications. ",
                "http://sendel.ru",
                "http://sendel.ru/wp-content/uploads/2012/10/1280px-05684-21-190x160.jpg"};
            bot.sendSingleSearchResult(chatId, answer);

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
