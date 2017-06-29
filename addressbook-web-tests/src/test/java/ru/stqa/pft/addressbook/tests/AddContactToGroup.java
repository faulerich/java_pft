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
    Contacts beforeContacts = app.db().contacts();

    Groups group = app.db().groups();
    ContactData selectedContact = beforeContacts.iterator().next(); //контакт для препарирования
    GroupData foundSituatedGroup = situatedGroup(group, selectedContact);


    Groups before = app.db().getContactFromDb(selectedContact.getId()).getGroups(); //получаем из БД группы, в которые входит выбранный контакт

    System.out.println("выбран контакт с id " + selectedContact.getId());
    System.out.println("выбранный контакт входит в группы " + before);

    //добавим выделенный контакт в найденную подходящую группу (если таковая имеется, а если нет (т.е. контакт уже добавлен во все группы) - создадим новую)
    app.contact().selectElementById(selectedContact.getId());
    app.contact().selectSituatedGroupFromList(foundSituatedGroup);
    System.out.println("контакт добавлен в группу с id " + foundSituatedGroup.getId());

    Groups after = app.db().getContactFromDb(selectedContact.getId()).getGroups(); //получаем из БД группы, в которые теперь входит выбранный контакт
    System.out.println("выбран контакт с id " + selectedContact.getId());
    System.out.println("выбранный контакт входит в группы " + after);



    /*
    дальнейшие действия:

    1) !!! определить, что будет если situatedGroup не найдена
    2) удаление контакта из группы
     */


  }
// получим подходящую для добавления группу

  public GroupData situatedGroup(Groups groups, ContactData contact) {
    Groups situatedGroups = contact.getGroups(); //получили все группы, в которые входит переданный в метод контакт
    for (GroupData group : groups) {
      if (situatedGroups.contains(group)) {
        continue;
      } else {   //если среди групп контакта нет очередной взятой из общего списка групп, то эта группа - наш клиент
        return group;
      }
    }
    return null;
  }


}
