package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Bond on 23.05.2017.
 */
public class ContactModificationTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    app.goTo().contactList();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "[none]"), true);
    }
  }

  @Test
  public void testContactModification() {

    app.goTo().contactList();
    List<ContactData> before = app.contact().list(); //получаем список элементов до модификации
    int index = before.size() - 1;
    app.contact().initContactModification(index+2);
    ContactData contact = new ContactData(before.get(index).getId(), "Yevgeny1", "Bondarenko2", "123", "test@test.com", "1985", "[none]"); //сохраняем старый идентификатор
    app.contact().modify(contact);

    List<ContactData> after = app.contact().list(); //получаем список элементов после модификации
    Assert.assertEquals(after.size(), before.size());

    before.remove(index); //удаляем последний элемент из списка
    before.add(contact); //вместо него добавляем тот, который должен появиться после модификации

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after); //сравниваем списки, т.к. они упорядочены по нашим правилам, написанным в компараторе

  }

}
