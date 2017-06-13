package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    app.goTo().contactList();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Yevgeny").withLastName( "Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactDeletion() {

    app.goTo().contactList();
    Contacts before = app.contact().all(); //получаем множество элементов до операции добавления
    System.out.println(before.size());
    app.goTo().contactList();

    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    assertThat(app.group().count(), equalTo(before.size() - 1));
    Contacts after = app.contact().all();
    System.out.println(after.size());//получаем множество элементов после операции добавления

    //чтобы убедиться в том, что контакт успешно удалился, мы сравниваем множества целиком: до удаления и после удаления
    assertThat(after, equalTo(before.withoutAdded(deletedContact)));

  }


}
