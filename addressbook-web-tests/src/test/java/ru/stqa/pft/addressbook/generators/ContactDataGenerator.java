package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
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

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);

    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.run();

  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format" + format);
    }
  }

  //сохраним в файл JSON список контактов, сформированный ниже в generateContacts
  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  //сохраним в файл XML список контактов, сформированный ниже в generateContacts
  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    //xstream.alias("contact", ContactData.class);
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  //сохраним в файл CSV список контактов, сформированный ниже в generateContacts
  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath()); //узнаем текущий путь
    try (Writer writer = new FileWriter(file)) {
      //цикл записи в файл для всех групп
      for (ContactData contact : contacts) {
        //writer.write(String.format("%s;%s;%s\n", contact.getFirstname(), contact.getLastname(), contact.getGroup()));
        writer.write(String.format("%s;%s;%s\n", contact.getFirstname(), contact.getLastname()));
      }
    }
  }

  //заполняем генератор
  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      //contacts.add(new ContactData().withFirstName(String.format("firstname %s", i)).withLastName(String.format("lastname %s", i)).withGroup(String.format("[none]"))
      contacts.add(new ContactData().withFirstName(String.format("firstname %s", i)).withLastName(String.format("lastname %s", i))
              .withHomephone(String.format("111 %s", i)).withEmail(String.format("test222@test222.com %s", i))
              .withBirthyear(String.format("1985")).withAddress(String.format("VRN %s", i)));
    }
    return contacts;
  }

}
