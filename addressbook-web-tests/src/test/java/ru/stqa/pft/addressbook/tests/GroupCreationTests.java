package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class GroupCreationTests extends TestBase {

  @DataProvider //для XML
  public Iterator<Object[]> validGroupsfromXML() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/group.xml"))); //создаем "читальщик"
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine(); // читаем строки
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml); //читаем данные из xml
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider  //для JSON
  public Iterator<Object[]> validGroupsfromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/group.json"))); //создаем "читальщик"
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine(); // читаем строки
    }
    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType());
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test(dataProvider = "validGroupsfromXML")
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

  @Test  (enabled = false) //негативный тест. проверяет, что нельзя создать группу с именем, содержащим апостроф
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
