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
public class AddContactToGroup extends TestBase {

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

  @Test

  public void testAddContactToGroup() {

    app.goTo().contactList();
    //app.contact().contactsFilterByGroup();
    Contacts beforeContacts = app.db().contacts();
    Groups before = app.db().groups(); //получаем из БД множество элементов до добавления контакта в группу
    Groups group = app.db().groups();
    ContactData selectedContact = beforeContacts.iterator().next(); //контакт для препарирования
    GroupData foundSituatedGroup = situatedGroup(group, selectedContact);
    app.contact().selectElementById(selectedContact.getId());

    //добавим выделенный контакт в найденную подходящую группу
    app.contact().selectSituatedGroupFromList(foundSituatedGroup.getId());
    app.contact().addContactToGroupButton();

    ContactData after = app.db().getContactFromDb(1); //получаем из БД множество элементов после добавления контакта в группу



    /*
    дальнейшие действия:
    1) найти before и after
    2) определить, что будет если situatedGroup не найдена
    3) удаление контакта из группы
     */


  }
// получим подходящую для добавления группу

  public GroupData situatedGroup(Groups groups, ContactData contact) {
    Groups situatedGroups = contact.getGroups(); //получили все группы, в которые входит переданный в метод контакт
    for (GroupData group : groups) {
      if (situatedGroups.contains(group)) {
        return null;
      } else {   //если среди групп контакта нет очередной взятой из общего списка групп, то эта группа - наш клиент
        return group;
      }
    }
    return null;
  }


}
