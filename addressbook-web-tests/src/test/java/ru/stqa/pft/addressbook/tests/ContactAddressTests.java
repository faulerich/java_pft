package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Bond on 14.06.2017.
 */
public class ContactAddressTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    app.goTo().contactList();
    if (app.contact().list().size() == 0) {
      //app.contact().create(new ContactData().withFirstName("Yevgeny").withLastName( "Bondarenko").withAddress("VRN").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]"), true);
      app.contact().create(new ContactData().withFirstName("Yevgeny").withLastName( "Bondarenko").withAddress("VRN").withHomephone("123").withEmail("test@test.com").withBirthyear("1985"), true);
    }
  }

  @Test
  public void testContactAddress() {
    app.goTo().contactList();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    //проверяем, что адрес на главной странице совпадает с адресом в поле редактирования контакта
    assertThat(contact.getFullAddress(), equalTo(contactInfoFromEditForm.getFullAddress()));
  }
}
