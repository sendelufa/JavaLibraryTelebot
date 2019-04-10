package javalibbot.Messages;

import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import javalibbot.MainContract;

public class TextMessage extends SendMessage implements MainContract.ViewMessages {

  Object chatId;
  String text;
  public TextMessage(Object chatId, String text) {
    super(chatId, text);
    this.chatId = chatId;
    this.text = text;
  }



  @Override
  public SendMessage text(String text) {
    return new SendMessage(chatId, this.text);
  }

  @Override
  public SendMessage startMessage() {
    return null;
  }

  @Override
  public SendMessage helpMessage() {
    return null;
  }

  @Override
  public SendMessage searchQueryText() {
    return null;
  }

  @Override
  public SendMessage book(String[] book) {
    return null;
  }

  @Override
  public SendMessage booksList(List<String[]> books) {
    return null;
  }
}
