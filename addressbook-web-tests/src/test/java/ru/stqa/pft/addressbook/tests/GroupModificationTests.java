package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Bond on 22.05.2017.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification() {

    Groups before = app.db().groups(); //получаем множество элементов до модификации
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withID(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3"); //сохраняем старый идентификатор
    app.goTo().groupPage();
    app.group().modify(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups(); //получаем множество элементов после модификации

    assertThat(after, equalTo(before.withoutAdded(modifiedGroup).withAdded(group)));

  }
}
