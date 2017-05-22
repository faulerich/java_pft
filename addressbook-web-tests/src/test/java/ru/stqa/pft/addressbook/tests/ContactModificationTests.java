package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by Bond on 23.05.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToContactList();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactCreationForm(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToContactList();
  }
}
