package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    initContactCreation();
    fillContactCreationForm(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985"));
    submitContactCreation();
  }

}
