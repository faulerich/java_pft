package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

//import sun.plugin2.util.BrowserType;

/**
 * Created by Bond on 20.05.2017.
 */
public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static ApplicationManager app;

  static {
    try {
      app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite (alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p) {
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod (alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

  public void verifyGroupListInUI() {

    //отключаемая проверка (загружать из БД или из интерфейса) (если системное св-во установлено, то выполнять описанные ниже действия)
    if (Boolean.getBoolean("verifyUI")) {   //функция, получающая системное св-во (в конфигурациях файла в св-во VM добавить -DverifyUI=true)

      Groups dbGroups = app.db().groups(); //список, загруженный из БД
      Groups uiGroups = app.group().all(); //список, загруженный из интерфейса

      //анонимная ф-ция, принимающая на вход группу, а на выходе будет новый объект типа GroupData
      // с именем таким же, как у преобразуемого объекта, а header и footer не будут указаны
      //после того, как ко всем эл-там применена эта функиця, нужно все это собрать при пом. коллектора toSet
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withID(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

}
