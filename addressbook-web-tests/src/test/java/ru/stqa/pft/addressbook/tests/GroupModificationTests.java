package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Bond on 22.05.2017.
 */
public class GroupModificationTests extends TestBase {

  @BeforeMethod   //перед каждым тестовым методом должна выполняться проверка предусловия
  public void ensurePreconditions() {  //проверяем предусловия: если список групп пуст, то создаем группу
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData("test1", "test2", "test3"));
    }
  }

  @Test
  public void testGroupModification() {

    List<GroupData> before = app.group().list(); //получаем список элементов до модификации
    int index = before.size() - 1;
    GroupData group = new GroupData(before.get(index).getId(), "test2", "test2", "test3"); //сохраняем старый идентификатор
    app.group().modify(index, group);
    List<GroupData> after = app.group().list(); //получаем список элементов после модификации
    Assert.assertEquals(after.size(), before.size());

    before.remove(index); //удаляем последний элемент из списка
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
