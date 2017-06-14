package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Bond on 14.06.2017.
 */
public class ContactPhoneTests extends TestBase {

  @Test
  public void testContactPhones() {
    app.goTo().contactList();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomephone(), contact.getMobilephone(), contact.getWorkphone())
     .stream().filter((s) -> ! s.equals(""))
            .map(ContactPhoneTests::cleaned)      //применить функцию ко всем элементам потока и вернуть поток, состоящий из результатов
            .collect(Collectors.joining("\n"));                      //коллектор, который склеивает все элементы потока в одну строку
  }

  public static String cleaned(String phone) { //функция удаляет возможные ненужные символы из телефонов
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
