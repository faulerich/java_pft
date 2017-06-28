package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

/**
 * Created by Bond on 27.06.2017.
 */
public class AddContactToGroup extends TestBase{

  @BeforeMethod
  public void ensureGroupPreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {  //проверяем предусловия: если список контактов пуст, то создаем контакт
    if (app.db().contacts().size() == 0) {
      app.goTo().contactList();
      app.contact().create(new ContactData()
              //.withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withAddress("qqq"), true);
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq"), true)
      ;

    }
  }
/*
  @BeforeMethod
  public void ensureContactPreconditions() {  //проверяем предусловия: если список контактов, не принадлежащих ни одной группе, пуст, то создаем контакт
    app.goTo().contactList();
    Groups groups =app.db().groups();
    app.contact().contactsFilterByGroup();
    if (app.contact().all().size() == 0) {
      app.goTo().contactList();
      app.contact().create(new ContactData()
              //.withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withAddress("qqq"), true);
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq").inGroup(groups.iterator().next()), true);

    }
  }
*/
  @Test

  public void testAddContactToGroup() {

    app.goTo().contactList();
    //app.contact().contactsFilterByGroup();
    Contacts before = app.db().contacts(); //получаем из БД множество элементов до добавления контакта в группу
    Groups group = app.db().groups();

    /*
    дальнейшие действия:
    1) создать методы для переходов (создания, удаления контакта из групп и др. по необходимости)
    2) создать метод (в другом классе), показывающий все контакты, которые не входят сразу во все группы
    3) сделать проверку на то, что список контактов из п.2 непустой (если пустой, то просто создать контакт)
    4) в тестовом классе:
      4.1) выбрать в качестве selectedContact контакт из п.2 (запомнить id)
      4.2) выбрать в качестве selectedGroup группу (запомнить id)
      4.3) произвести добавление контакта в группу
      4.4) взять after для контактов и сравнить (по аналогии с ContactModificationTest)
     */



    ContactData selectedContact = before.iterator().next(); //контакт для препарирования
    System.out.println(selectedContact.getId());//знаем id выбранного контакта
    System.out.println(selectedContact);
    System.out.println(selectedContact.getGroups());

    /*
    app.contact().initContactModification(selectedContact.getId());
    ContactData contact = new ContactData()
            //.withID(modifiedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]").withAddress("qqq2"); //сохраняем старый идентификатор
            .withID(selectedContact.getId()).withFirstName("Yevgeny2").withLastName("Bondarenko2").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq2"); //сохраняем старый идентификатор
    app.contact().modify(contact);

    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts(); //получаем множество элементов после модификации
    //Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withoutAdded(selectedContact).withAdded(contact)));

    verifyContactListInUI();*/

  }

}
