package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Bond on 23.05.2017.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    if (app.db().contacts().size() == 0) {
      app.goTo().contactList();
      app.contact().create(new ContactData()
              //.withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withAddress("qqq"), true);
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq"), true)
      ;

    }
  }

  @Test
  public void testContactModification() {

    app.goTo().contactList();
    Contacts before = app.db().contacts(); //получаем множество элементов до модификации
    //Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    System.out.println(modifiedContact.getId());
    app.contact().initContactModification(modifiedContact.getId());
    ContactData contact = new ContactData()
            //.withID(modifiedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withAddress("qqq2"); //сохраняем старый идентификатор
            .withID(modifiedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq2"); //сохраняем старый идентификатор
    app.contact().modify(contact);

    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts(); //получаем множество элементов после модификации
    //Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withoutAdded(modifiedContact).withAdded(contact)));

  }

}
