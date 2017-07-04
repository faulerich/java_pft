package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.tests.TestBase;

/**
 * Created by Bond on 04.07.2017.
 */
public class RegistrationHelper {

  private final ApplicationManager app;
  private WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    this.app = app;
    //нас не устраивает то, что инициализация менеджера происходит слишком рано,
    // поэтому поменяем на т.н. "ленивую инициализацию"
    wd = app.getDriver(); //этот метод будет инициализировать драйвер в момент первого обращения
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
  }
}
