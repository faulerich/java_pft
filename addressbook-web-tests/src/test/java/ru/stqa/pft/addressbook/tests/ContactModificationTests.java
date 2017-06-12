package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Bond on 23.05.2017.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    app.goTo().contactList();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactModification() {

    app.goTo().contactList();
    Contacts before = app.contact().all(); //получаем множество элементов до модификации
    ContactData modifiedContact = before.iterator().next();
    System.out.println(modifiedContact.getId());
    app.contact().initContactModification(modifiedContact.getId());
    ContactData contact = new ContactData()
            .withID(modifiedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]"); //сохраняем старый идентификатор
    app.contact().modify(contact);

    Contacts after = app.contact().all(); //получаем множество элементов после модификации
    assertEquals(after.size(), before.size());

    assertThat(after, equalTo(before.withoutAdded(modifiedContact).withAdded(contact)));

  }

}
