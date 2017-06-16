package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new ContactData().withFirstName("Yevgeny11").withLastName("Bondarenko11").withGroup("[none]")});
    list.add(new Object[]{new ContactData().withFirstName("Yevgeny22").withLastName("Bondarenko22").withGroup("[none]")});
    list.add(new Object[]{new ContactData().withFirstName("Yevgeny33").withLastName("Bondarenko33").withGroup("[none]")});
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void contactGroupCreation(ContactData contact) {

    app.goTo().contactList();
    Contacts before = app.contact().all(); //получаем множество элементов до операции добавления

    app.contact().create(contact, true);
    app.goTo().contactList();
    Contacts after = app.contact().all(); //получаем множество элементов после операции добавления

    assertEquals(after.size(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all

    //используем библиотеку Hamcrest и сравниваем списки
    assertThat(after, equalTo
            (before.withAdded(contact.withID(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }

  @Test  (enabled = false) //вспомогательный тест, который определяет, какая директория является рабочей
  public void testCurrentDir() {
    File currentDir = new File(".");

    //определим абсолютный путь к директории
    System.out.println(currentDir.getAbsolutePath());

    //убедимся, что файл существует
    File photo = new File("src/test/resources/pchelovodstvo.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

  @Test  (enabled = false) //негативный тест. проверяет, что нельзя создать контакт с именем, содержащим апостроф
  public void contactBadGroupCreation() {

    app.goTo().contactList();
    Set<ContactData> before = app.contact().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    ContactData contact = new ContactData()
            .withFirstName("Yevgeny").withLastName("Bondarenko'").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]");
    app.contact().create(contact, true);
    app.goTo().contactList();

    assertEquals(app.contact().count(), before.size()); //сравниваем размеры множеств, которые получены методом all
    Set<ContactData> after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
