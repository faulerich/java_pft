package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Bond on 30.06.2017.
 */
public class DeleteContactFromGroup extends TestBase {

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
              .withFirstName("Yevgeny").withLastName("Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withAddress("qqq"), true);
    }
  }

  @Test

  public void testDeleteContactFromGroup() {

  Contacts beforeContacts = app.db().situatedGroupForRemoveContact().getContacts(); //список контактов в подобранной группе на данный момент
    int situatedGroupIDForRemoveContact = app.db().situatedGroupForRemoveContact().getId();
    System.out.println("в подобранной группе id " + app.db().situatedGroupForRemoveContact().getId() + " контакты: " + beforeContacts);
    app.goTo().contactList();
    app.contact().contactsFilterByGroup(situatedGroupIDForRemoveContact);
    ContactData selectedContact = beforeContacts.iterator().next(); //контакт для препарирования
    app.contact().selectElementById(selectedContact.getId());
    app.contact().deleteContactFromGroupButton();
    app.goTo().contactList();
    Contacts afterContacts = app.db().situatedGroupForRemoveContact().getContacts(); //список контактов в подобранной группе на данный момент (после удаление из нее контакта)
    System.out.println("в подобранной группе id " + app.db().situatedGroupForRemoveContact().getId() + " контакты: " + afterContacts);

    assertThat(afterContacts, equalTo(beforeContacts.withoutAdded(selectedContact)));
}

}
