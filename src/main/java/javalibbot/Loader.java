package javalibbot;

import javalibbot.MainContract.Bot;
import javalibbot.Model.TeleBot;
import javalibbot.config.ConfigContainer;


public class Loader {

  public static void main(String[] args) {

    //read config strings
    ConfigContainer.read();

    // Create bot and push to presenter
    final Bot bot = new TeleBot(ConfigContainer.getToken());


    // for tests
    // bot.startUpdate(3000);
  }
}
