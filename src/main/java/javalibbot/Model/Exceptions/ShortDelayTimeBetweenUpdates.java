package javalibbot.Model.Exceptions;

import javalibbot.MainContract.LogAction;

public class ShortDelayTimeBetweenUpdates extends Exception{

  public ShortDelayTimeBetweenUpdates(){
    LogAction.severe("Слишком короткие промежутки между запросами к серверу Telegram");
  }

}
