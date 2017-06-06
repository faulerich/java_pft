package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList(); //получаем список элементов до операции добавления

    System.out.println(before.size());
    GroupData group = new GroupData("test3", "test2", "test3");
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList(); //получаем список элементов после операции добавления

    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры списков, которые получены методом getGroupList
    System.out.println(after.size());


    /*когда мы создаем новую группу, то у нее никакого "старого" идентификатора нет, т.к. присваивается новый идентификатор
    * нужно узнать, какой именно идентификатор был присвоен созданной группе. А ей присвоен был идентификатор, максимальный среди всех.
    * для его нахождения тип идентификтора надо поменять со String на Int*/



    //создаем новый объект типа "компаратор"
   // Comparator<? super GroupData> byId = (Comparator<GroupData>) (o1, o2) -> Integer.compare(o1.getId(), o2.getId());


    //превращаем список в поток и вычислим максимальный элемент в потоке (max),
    // передав ему анонимную функцию (лямбда-выражение)
    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId); //сортируем "старый" список
    after.sort(byId); //сортируем "новый" список
    Assert.assertEquals(before, after); //сравниваем списки


  }

}
