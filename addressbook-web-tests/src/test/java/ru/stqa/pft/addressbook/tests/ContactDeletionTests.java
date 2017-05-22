package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().goToContactList();
    app.getGroupHelper().selectElement();
    app.getContactHelper().deleteSelectedContacts();
  }

}
