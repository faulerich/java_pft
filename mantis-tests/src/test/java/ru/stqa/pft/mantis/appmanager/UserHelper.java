package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.tests.TestBase;

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
}
