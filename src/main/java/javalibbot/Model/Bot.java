package javalibbot.Model;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import javalibbot.MainContract;
import javalibbot.MainContract.ViewMessages;

public class Bot implements MainContract.Bot {

  @Override
  public void startBot(String Token) {

  }

  @Override
  public List<Update> getUpdates(int limit, int offset) {
    return null;
  }

  @Override
  public void sendMessage(ViewMessages message) {

  }
}
