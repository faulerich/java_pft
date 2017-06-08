package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    }
  }

  @Test
  public void testGroupDeletion() {

    List<GroupData> before = app.getGroupHelper().getGroupList(); //получаем список элементов до операции добавления
    app.getGroupHelper().selectElement(before.size() - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList(); //получаем список элементов после операции добавления
    Assert.assertEquals(after.size(), before.size() - 1);

    //чтобы убедиться в том, что группа корректно удалилась, мы сравниваем списки целиком: до удаления и после удаления
    before.remove(before.size() - 1); //последний элемент
    Assert.assertEquals(before, after);

  }

}
