package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
//import sun.plugin2.util.BrowserType;
import org.openqa.selenium.remote.BrowserType;

/**
 * Created by Bond on 20.05.2017.
 */
public class TestBase {

  protected final ApplicationManager app = new ApplicationManager(BrowserType.IE);

  @BeforeMethod
  public void setUp() throws Exception {

    app.init();
  }

  @AfterMethod
  public void tearDown() {
    app.stop();
  }

}
