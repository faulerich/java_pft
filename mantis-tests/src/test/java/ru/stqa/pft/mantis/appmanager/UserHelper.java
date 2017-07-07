package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;

/**
 * Created by Bond on 04.07.2017.
 */
public class UserHelper extends HelperBase {


  public UserHelper(ApplicationManager app) {
    super(app);
  }

  //переход к разделу Manage
  public void openManageSection() {
    click(By.xpath("//a[text() = 'Manage']"));
  }

  //переход к разделу ManageUsers
  public void openManageUsersSection() {
    click(By.xpath("//a[text() = 'Manage Users']"));
  }

  //открытие странички пользователя по имени
  public void openUserByUsername(String name) {
    click(By.xpath("//a[text() = '" + name + "']"));
  }

  //инициация смены пароля
  public void initPasswordReset() {
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Update User']"));
  }

  public void login(String login, String password) {
    type(By.name("username"), login);
    type(By.name("password"), password);
    click(By.xpath("//input[@value='Login']"));
  }

  public void adminLogin() {
    type(By.name("username"), "administrator");
    type(By.name("password"), "root");
    click(By.xpath("//input[@value='Login']"));
  }

  public String generatedPassword() {
    int randomValue = (int) (Math.random() * 100000);
    return Integer.toString(randomValue);
  }

  //логаут
  public void logout() {
    click(By.xpath("//a[text() = 'Logout']"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
}
