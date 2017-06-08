package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

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

    List<GroupData> before = app.group().list(); //получаем список элементов до операции добавления
    int index = before.size() - 1;
    app.group().delete(index);
    List<GroupData> after = app.group().list(); //получаем список элементов после операции добавления
    Assert.assertEquals(after.size(), before.size() - 1);

    //чтобы убедиться в том, что группа корректно удалилась, мы сравниваем списки целиком: до удаления и после удаления
    before.remove(index); //последний элемент
    Assert.assertEquals(before, after);

  }

}
