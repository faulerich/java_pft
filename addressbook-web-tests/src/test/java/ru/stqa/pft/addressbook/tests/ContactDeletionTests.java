package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().goToContactList();

    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Yevgeny", "Bondarenko", "123", "test@test.com", "1985", "[none]"), true);
    }

    app.getNavigationHelper().goToContactList();
    List<ContactData> before = app.getContactHelper().getContactList(); //получаем список элементов до операции добавления
    System.out.println(before.size());
    app.getNavigationHelper().goToContactList();
    app.getGroupHelper().selectElement(before.size() - 1);
    app.getContactHelper().deleteSelectedContacts();
    app.getNavigationHelper().goToContactList();

    List<ContactData> after = app.getContactHelper().getContactList();
    System.out.println(after.size());//получаем список элементов после операции добавления
    Assert.assertEquals(after.size(), before.size() - 1);

    //чтобы убедиться в том, что контакт успешно удалился, мы сравниваем списки целиком: до удаления и после удаления
    before.remove(before.size() - 1); //последний элемент
    Assert.assertEquals(before, after);

  }

}
