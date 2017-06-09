package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

    Set<GroupData> before = app.group().all(); //получаем множество элементов до модификации
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withID(modifiedGroup.getId()).withName("test2").withHeader("test3").withFooter("test4"); //сохраняем старый идентификатор
    app.group().modify(group);
    Set<GroupData> after = app.group().all(); //получаем множество элементов после модификации
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedGroup); //удаляем последний элемент из списка
    before.add(group); //вместо него добавляем тот, который должен появиться после модификации

    Assert.assertEquals(before, after); //сравниваем списки, т.к. они упорядочены по нашим правилам, написанным в компараторе

  }
}
