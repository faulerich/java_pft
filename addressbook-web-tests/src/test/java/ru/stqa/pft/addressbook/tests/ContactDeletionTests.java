package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().goToContactList();

    if (!app.getGroupHelper().isThereAGroup()) {
      app.getContactHelper().createContact(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "[none]"), true);
    }
    app.getNavigationHelper().goToContactList();
    app.getGroupHelper().selectElement(0);
    app.getContactHelper().deleteSelectedContacts();
    app.getNavigationHelper().goToContactList();
  }

}
