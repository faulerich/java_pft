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
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getContactHelper().createContact(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "test1"), true);
    }
    app.getNavigationHelper().goToContactList();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactCreationForm(new ContactData("Yevgeny1", "Bondarenko2", "123", "test@test.com", "1985", "test1"), false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToContactList();
  }
}
