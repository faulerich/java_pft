package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() {

    Groups before = app.group().all(); //получаем множество элементов до операции добавления
    //элемент из списка выбирается случайным образом
    //получим сначала итератор, который позволяет последовательно перебирать элементы, а потом вызвать next, который вернет первый попавшийся эл-т множества
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);

    assertThat(app.group().count(), equalTo(before.size() - 1));
    Groups after = app.group().all(); //получаем множество элементов после операции добавления

    //чтобы убедиться в том, что группа корректно удалилась, мы сравниваем множества целиком: до удаления и после удаления
    assertThat(after, equalTo(before.withoutAdded(deletedGroup)));


  }

}
