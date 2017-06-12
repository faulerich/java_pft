package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Bond on 22.05.2017.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification() {

    Groups before = app.group().all(); //получаем множество элементов до модификации
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withID(modifiedGroup.getId()).withName("test2").withHeader("test3").withFooter("test4"); //сохраняем старый идентификатор
    app.group().modify(group);
    Groups after = app.group().all(); //получаем множество элементов после модификации
    assertEquals(after.size(), before.size());

    assertThat(after, equalTo(before.withoutAdded(modifiedGroup).withAdded(group)));

  }
}
