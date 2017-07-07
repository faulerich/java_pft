package ru.stqa.pft.mantis.tests;

import com.sun.jna.platform.win32.Netapi32Util;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Bond on 06.07.2017.
 */
public class PasswordResetTest extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void PasswordResetTest() throws IOException, InterruptedException, MessagingException {

    
    //Администратор входит в систему
    app.user().login("administrator", "root");
    //Администратор переходит на страницу управления пользователями
    app.user().openManageSection();
    app.user().openManageUsersSection();
    //Администратор выбирает некоторого пользователя и инициирует смену пароля (жмет Reset Password)
    UserData situatedUser = app.db().getSituatedUsers().iterator().next();
    String situatedUserEmail = situatedUser.getEmail();
    System.out.println("выбран пользователь " + situatedUser);
    app.user().openUserByUsername(situatedUser.getUsername());
    app.user().initPasswordReset();
    //Отправляется письмо на email выбранного пользователя
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    //получаем это письмо

    //извлекаем ссылку из этого письма

    //переходим по ссылке и меняем пароль

    //Проверим, что пользователь может войти в систему под новым паролем

  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
