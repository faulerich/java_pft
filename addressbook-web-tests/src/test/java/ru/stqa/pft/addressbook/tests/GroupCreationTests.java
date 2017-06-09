package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    GroupData group = new GroupData().withName("test3");
    app.group().create(group);
    Set<GroupData> after = app.group().all(); //получаем множество элементов после операции добавления

    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all
    System.out.println(after.size());

//превращаем поток объектов типа GroupData в поток идентификаторов с пом. mapToInt
    //в качестве параметра он принимает анонимную функцию, у которой в качестве параметра указана группа, а в качестве результата - ее идентификатор
    group.withID(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()); //вычислили максимальный среди всех идентификаторов
    before.add(group);
    Assert.assertEquals(before, after); //сравниваем списки


  }

}
