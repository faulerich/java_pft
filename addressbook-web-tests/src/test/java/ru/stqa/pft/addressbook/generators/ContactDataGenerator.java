package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bond on 16.06.2017.
 */
public class ContactDataGenerator {

  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);  //путь к файлу

    List<ContactData> contacts = generateContacts(count);
    save(contacts, file);
  }

  //сохраним в файл список контактов, сформированный ниже
  private static void save(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath()); //узнаем текущий путь
    Writer writer = new FileWriter(file);
    //цикл записи в файл для всех групп
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s\n", contact.getFirstname(), contact.getLastname()));
    }
    writer.close();
  }

  //заполняем генератор
  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstName(String.format("firstname %s", i)).withLastName(String.format("lastname %s", i)));
    }
    return contacts;
  }

}
