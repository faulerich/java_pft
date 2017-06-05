package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Bond on 22.05.2017.
 */
public class GroupModificationTests extends TestBase {

  @Test
  public void testGroupModification() {
    app.getNavigationHelper().goToGroupPage();
    if (!app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    }
    List<GroupData> before = app.getGroupHelper().getGroupList(); //получаем список элементов до операции добавления
    app.getGroupHelper().selectElement(before.size() - 1); //выбираем последнюю группу
    app.getGroupHelper().initGroupModification();
    GroupData group = new GroupData(before.get(before.size() - 1).getId(), "test2", "test2", "test3"); //сохраняем старый идентификатор
    app.getGroupHelper().fillGroupForm(group);
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList(); //получаем список элементов после операции добавления
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1); //удаляем последний элемент из списка
    before.add(group); //вместо него добавляем тот, который должен появиться после модификации

    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after); //сравниваем списки, т.к. они упорядочены по нашим правилам, написанным в компараторе

    /*обратить внимание, что, если в результате модификации получится так, что несколько групп будут иметь одинаковые имена,
    * то тест выполнится некорректно: множества не допускают неуникальных элементов => все элементы с одинаковыми названия будут схлопываться
    * (так, размер множества [test1, test1, test2] будет равен 2)
    * Возможный выход - нужен уникальный идентификатор для каждого объекта. Таковым является значение value (см. инспектор объектов)*/

  }
}
