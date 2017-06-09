package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

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
    Set<ContactData> before = app.contact().all(); //получаем множество элементов до операции добавления
    System.out.println(before.size());
    app.goTo().contactList();

    ContactData deletedContact = before.iterator().next();

    app.contact().delete(deletedContact);

    Set<ContactData> after = app.contact().all();
    System.out.println(after.size());//получаем множество элементов после операции добавления
    Assert.assertEquals(after.size(), before.size() - 1);

    //чтобы убедиться в том, что контакт успешно удалился, мы сравниваем множества целиком: до удаления и после удаления
    before.remove(deletedContact); //последний элемент
    Assert.assertEquals(before, after);

  }


}
