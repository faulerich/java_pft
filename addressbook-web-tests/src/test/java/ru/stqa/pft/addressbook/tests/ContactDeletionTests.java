package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    app.goTo().contactList();
    if (app.group().list().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Yevgeny").withLastName( "Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("test1"), true);
    }
  }

  @Test
  public void testContactDeletion() {

    app.goTo().contactList();
    List<ContactData> before = app.contact().list(); //получаем список элементов до операции добавления
    int index = before.size() - 1;
    System.out.println(before.size());
    app.goTo().contactList();
    app.contact().delete(index);

    List<ContactData> after = app.contact().list();
    System.out.println(after.size());//получаем список элементов после операции добавления
    Assert.assertEquals(after.size(), before.size() - 1);

    //чтобы убедиться в том, что контакт успешно удалился, мы сравниваем списки целиком: до удаления и после удаления
    before.remove(index); //последний элемент
    Assert.assertEquals(before, after);

  }


}
