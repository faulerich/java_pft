package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList(); //получаем список элементов до операции добавления

    System.out.println(before.size());
    GroupData group = new GroupData("test1", "test2", "test3");
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList(); //получаем список элементов после операции добавления

    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры списков, которые получены методом getGroupList
    System.out.println(after.size());


    /*когда мы создаем новую группу, то у нее никакого "старого" идентификатора нет, т.к. присваивается новый идентификатор
    * нужно узнать, какой именно идентификатор был присвоен созданной группе. А ей присвоен был идентификатор, максимальный среди всех.
    * для его нахождения тип идентификтора надо поменять со String на Int*/


    int max = 0;
    for (GroupData g : after) {
      if (g.getId() > max) {
        max = g.getId();
      }
    }
    group.setId(max);
    before.add(group);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after)); //сравниваем не списки, а множества (т.к. они упорядочены)


  }

}
