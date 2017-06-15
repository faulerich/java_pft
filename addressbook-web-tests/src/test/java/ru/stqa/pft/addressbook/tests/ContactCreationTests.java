package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    app.goTo().contactList();
    Contacts before = app.contact().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    File photo = new File("src/test/resources/pchelovodstvo.jpg");  //указываем относительный путь к файлу (путь относительно рабочей директории программы.
                                                                              // рабочая директория вычисляется методом getAbsolutePath)
    ContactData contact = new ContactData()
            .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withPhoto(photo);
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
