package javalibbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;

public class Loader {

  public static void main(String[] args) {
    System.out.println("Start bot");



    // Create your bot passing the token received from @BotFather
    final TelegramBot bot = new TelegramBot("873714095:AAFYGJh_SPR112ueJHVhaeJoDblJjOSbHlI");

    Thread th = new Thread() {
      public void run() {
        int m = 0;
        while (true) {
          List<Update> update = bot.execute(new GetUpdates().limit(100).offset(m)).updates();

          for (Update up : update) {
            m = up.updateId() + 1;
            String text = up.message().text();
            System.out.println(up.message().text());
            if (text.toLowerCase().contains("привет")){
              bot.execute(new SendMessage(up.message().chat().id(), "привет! каг дила?"));
            }
            else {
              bot.execute(new SendMessage(up.message().chat().id(), up.message().text()));
            }

          }

          try {
            sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          System.out.println("getUpdate " + m);
        }
      }
    };

    th.start();
  }
}
