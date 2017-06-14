package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Bond on 14.06.2017.
 */
public class ContactAddressTests extends TestBase {

  @Test
  public void testContactAddress() {
    app.goTo().contactList();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    //проверяем, что адрес на главной странице совпадает с адресом в поле редактирования контакта
    assertThat(contact.getFullAddress(), equalTo(contactInfoFromEditForm.getFullAddress()));
  }
}
