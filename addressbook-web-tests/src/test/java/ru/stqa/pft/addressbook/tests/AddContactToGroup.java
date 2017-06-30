package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

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
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq"), true);
    }
  }

  @Test
  public void testAddContactToGroup() {

    GroupData foundSituatedGroup = null;
    Contacts contactsBefore = null;

    app.goTo().contactList();

    Contacts beforeContacts = app.db().contacts();
    Groups group = app.db().groups();

    ContactData selectedContact = beforeContacts.iterator().next(); //контакт для препарирования
    foundSituatedGroup = app.db().situatedGroup(group, selectedContact);

    //добавим выделенный контакт в найденную подходящую группу (если таковая имеется, а если нет (т.е. контакт уже добавлен во все группы) - создадим новую)
    if (foundSituatedGroup == null) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test_new"));
      //добавлена новая группа, найдем ее по максимальному id среди всех групп
      foundSituatedGroup = app.db().getGroupWithMaxIDFromDb();
      contactsBefore = app.db().getGroupFromDb(foundSituatedGroup.getId()).getContacts();
      app.goTo().contactList();
    } else {
      contactsBefore = app.db().getGroupFromDb(foundSituatedGroup.getId()).getContacts();
    }

    app.contact().selectElementById(selectedContact.getId());
    app.contact().selectSituatedGroupFromList(foundSituatedGroup);

    //надо выбрать контакты, которые на этот момент находятся в группе foundSituatedGroup
    Contacts contactsAfter = app.db().getGroupFromDb(foundSituatedGroup.getId()).getContacts();

    assertThat(contactsAfter, equalTo(contactsBefore.withAdded(selectedContact)));
  }

}
