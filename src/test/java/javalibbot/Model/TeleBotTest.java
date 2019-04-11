package javalibbot.Model;

import javalibbot.MainContract.Bot;
import javalibbot.Model.Exceptions.ShortDelayTimeBetweenUpdates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeleBotTest {

  private Bot bot;
  private long chatId = 1;
  @Before
  public void setUp() throws Exception {
    bot = new TeleBot("");
  }

  @Test
  public void sendTextMessage() {
  }

  @Test (expected = ArrayIndexOutOfBoundsException.class)
  public void sendSingleSearchResult_ANSWER_ARRAYLENGHT() throws ArrayIndexOutOfBoundsException{
    String[] answer = {};
    bot.sendSingleSearchResult(chatId, answer);
    String[] answer1 = {"1"};
    bot.sendSingleSearchResult(chatId, answer1);
    String[] answer2 = {"1", "2"};
    bot.sendSingleSearchResult(chatId, answer2);
    String[] answer3 = {"1", "2", "3"};
    bot.sendSingleSearchResult(chatId, answer3);
  }

  @Test
  public void sendSingleSearchResult_ANSWER_RIGHTLENGHT() {
    String[] answer = {"1", "2", "3", "4"};
    bot.sendSingleSearchResult(chatId, answer);
  }

  @Test (expected = ShortDelayTimeBetweenUpdates.class)
  public void startUpdate_MINIMUM_DELAY_TIME() throws ShortDelayTimeBetweenUpdates {
    bot.startUpdate(499);
    Assert.assertEquals(500, bot.getDelay());
  }

  @Test
  public void sendTextMessage_TEXT_NULL() {
    bot.sendTextMessage(0, null);
  }

  @Test
  public void bot_CREATE_NULL_TOKEN() {
    Bot testBot = new TeleBot(null);
    Assert.assertNotNull(testBot);
  }
}