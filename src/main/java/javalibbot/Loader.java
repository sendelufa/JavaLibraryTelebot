package javalibbot;

import javalibbot.MainContract.Bot;
import javalibbot.MainContract.ProcessQuery;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;
import javalibbot.Model.TeleBot;
import javalibbot.Presenter.TempClass;
import javalibbot.config.ConfigContainer;


public class Loader {

  public static void main(String[] args) throws ShortDelayTimeBetweenUpdates {

    //read config strings
    ConfigContainer.read();

    // Create bot and push to presenter
    final Bot bot = new TeleBot(ConfigContainer.getToken());

    //place for start presenter

    //bot.startUpdate() - for tests

    // for tests
    bot.startUpdate(ConfigContainer.getDelayUpdate());

    ProcessQuery processQuery = new TempClass();
    processQuery.startQueryProcess(bot);
  }
}
