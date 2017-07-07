package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by Bond on 04.07.2017.
 */
public class RegistrationTests extends TestBase{

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis(); //возвращает текущее время
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost", now);

    app.james().createUser(user, password);

    app.registration().start(user, email);
    //ожидание почты (ждем 2 письма на протяжении 10 секунд)
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    //List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);

    //в следующий метод будет передаваться список писем и адрес эл.почты
    //будем находить среди всех писем то, которое отправлено на этот эл.адрес и извлекать из него ссылку
    String confirmationLink = findConfirmationLink(mailMessages, email);

    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }


  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
