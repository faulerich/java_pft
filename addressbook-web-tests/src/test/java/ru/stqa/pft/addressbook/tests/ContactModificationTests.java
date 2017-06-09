package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
    Set<ContactData> before = app.contact().all(); //получаем множество элементов до модификации
    ContactData modifiedContact = before.iterator().next();
    System.out.println(modifiedContact.getId());
    app.contact().initContactModification(modifiedContact.getId());
    ContactData contact = new ContactData()
            .withID(modifiedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]"); //сохраняем старый идентификатор
    app.contact().modify(contact);

    Set<ContactData> after = app.contact().all(); //получаем множество элементов после модификации
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact); //удаляем последний элемент из множества
    before.add(contact); //вместо него добавляем тот, который должен появиться после модификации

    Assert.assertEquals(before, after); //сравниваем множества

  }

}
