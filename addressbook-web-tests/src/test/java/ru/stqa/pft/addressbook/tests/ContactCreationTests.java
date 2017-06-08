package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    app.goTo().contactList();
    List<ContactData> before = app.contact().list(); //получаем список элементов до операции добавления

    System.out.println(before.size());
    ContactData contact = new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "test1");
    app.contact().create(contact, true);
    app.goTo().contactList();
    List<ContactData> after = app.contact().list(); //получаем список элементов после операции добавления
    System.out.println(after.size());
    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры списков, которые получены методом list


    //превращаем список в поток и вычислим максимальный элемент в потоке (max),
    // передав ему анонимную функцию (лямбда-выражение)
    //contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId); //сортируем "старый" список
    after.sort(byId); //сортируем "новый" список
    Assert.assertEquals(before, after); //сравниваем списки

  }

}
