package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    app.goTo().contactList();
    Set<ContactData> before = app.contact().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    ContactData contact = new ContactData()
            .withFirstName("Yevgeny").withLastName( "Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]");
    app.contact().create(contact, true);
    app.goTo().contactList();
    Set<ContactData> after = app.contact().all(); //получаем множество элементов после операции добавления
    System.out.println(after.size());
    Assert.assertEquals(after.size(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all


    //превращаем список в поток и вычислим максимальный элемент в потоке (max),
    // передав ему анонимную функцию (лямбда-выражение)
    //contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());

    //превращаем поток объектов типа ContactData в поток идентификаторов с пом. mapToInt
    //в качестве параметра он принимает анонимную функцию, у которой в качестве параметра указан контакт, а в качестве результата - его идентификатор
    contact.withID(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()); //вычислили максимальный среди всех идентификаторов
    before.add(contact);
    Assert.assertEquals(before, after); //сравниваем списки

  }

}
