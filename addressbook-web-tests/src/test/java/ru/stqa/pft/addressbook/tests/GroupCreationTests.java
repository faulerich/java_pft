package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList(); //получаем список элементов до операции добавления

    System.out.println(before.size());
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    List<GroupData> after = app.getGroupHelper().getGroupList(); //получаем список элементов после операции добавления

    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры списков, которые получены методом getGroupList
    System.out.println(after.size());
  }

}
