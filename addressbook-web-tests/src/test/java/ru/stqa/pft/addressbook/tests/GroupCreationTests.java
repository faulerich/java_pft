package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new GroupData().withName("test 1").withHeader("header 1").withFooter("footer 1")});
    list.add(new Object[]{new GroupData().withName("test 2").withHeader("header 2").withFooter("footer 2")});
    list.add(new Object[]{new GroupData().withName("test 3").withHeader("header 3").withFooter("footer 3")});
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all(); //получаем множество элементов до операции добавления

    app.group().create(group);
    assertEquals(app.group().count(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all
    Groups after = app.group().all(); //получаем множество элементов после операции добавления


    // System.out.println(after.size());

    //превращаем поток объектов типа GroupData в поток идентификаторов с пом. mapToInt
    //в качестве параметра он принимает анонимную функцию, у которой в качестве параметра указана группа, а в качестве результата - ее идентификатор

    //используем библиотеку Hamcrest и сравниваем списки
    assertThat(after, equalTo
            (before.withAdded(group.withID(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));


  }

  @Test  //негативный тест. проверяет, что нельзя создать группу с именем, содержащим апостроф
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    GroupData group = new GroupData().withName("test3'");
    app.group().create(group);

    assertEquals(app.group().count(), before.size());
    Groups after = app.group().all(); //получаем множество элементов после операции добавления

    assertThat(after, equalTo(before));
  }

}
