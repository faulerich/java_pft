package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {


  @Test
  public void testGroupDeletion() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    }
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
