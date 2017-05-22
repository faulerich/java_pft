package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactCreationForm(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985"));
    app.getContactHelper().submitContactCreation();
  }

}
