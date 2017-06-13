package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    GroupData group = new GroupData().withName("test3");
    app.group().create(group);
    Groups after = app.group().all(); //получаем множество элементов после операции добавления

    assertEquals(after.size(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all
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
