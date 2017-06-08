package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Bond on 23.05.2017.
 */
public class ContactModificationTests extends TestBase {

  @Test(enabled = false)
  public void testContactModification() {
    app.getNavigationHelper().goToContactList();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "[none]"), true);
    }
    app.getNavigationHelper().goToContactList();
    List<ContactData> before = app.getContactHelper().getContactList(); //получаем список элементов до модификации
    app.getContactHelper().initContactModification(before.size() + 1);
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Yevgeny1", "Bondarenko2", "123", "test@test.com", "1985", "[none]"); //сохраняем старый идентификатор
    app.getContactHelper().fillContactCreationForm(contact, false);
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().goToContactList();

    List<ContactData> after = app.getContactHelper().getContactList(); //получаем список элементов после модификации
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1); //удаляем последний элемент из списка
    before.add(contact); //вместо него добавляем тот, который должен появиться после модификации

    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after); //сравниваем списки, т.к. они упорядочены по нашим правилам, написанным в компараторе

  }
}
