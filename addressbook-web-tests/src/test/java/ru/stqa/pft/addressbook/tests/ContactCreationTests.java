package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

  @Test
  public void contactGroupCreation() {

    app.goTo().contactList();
    Contacts before = app.contact().all(); //получаем множество элементов до операции добавления

    System.out.println(before.size());
    ContactData contact = new ContactData()
            .withFirstName("Yevgeny").withLastName( "Bondarenko").withHomephone("123").withEmail("test@test.com").withBirthyear("1985").withGroup("[none]");
    app.contact().create(contact, true);
    app.goTo().contactList();
    Contacts after = app.contact().all(); //получаем множество элементов после операции добавления
    //System.out.println(after.size());
    assertEquals(after.size(), before.size() + 1); //сравниваем размеры множеств, которые получены методом all


    //превращаем список в поток и вычислим максимальный элемент в потоке (max),
    // передав ему анонимную функцию (лямбда-выражение)
    //contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());

    //превращаем поток объектов типа ContactData в поток идентификаторов с пом. mapToInt
    //в качестве параметра он принимает анонимную функцию, у которой в качестве параметра указан контакт, а в качестве результата - его идентификатор

    //используем библиотеку Hamcrest и сравниваем списки
    assertThat(after, equalTo
            (before.withAdded(contact.withID(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }

}
