package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bond on 21.05.2017.
 */
public class GroupHelper extends HelperBase {

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type(By.name("group_name"), groupData.getName());
    type(By.name("group_header"), groupData.getHeader());
    type(By.name("group_footer"), groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectElement(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectElementById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) {
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public boolean isThereAGroup() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();     //получаем размер списка элементов
  }

  public void modify(GroupData group) {
    selectElementById(group.getId()); //выбираем последнюю группу
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(int index) {
    selectElement(index);
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectElementById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }

  //метод для получения списка групп
  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //выбираем список всех элементов с css-селектором span.group
    for (WebElement element : elements) { //переменная element пробегает по списку elements
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //ищем элемент внутри элемента
     // GroupData group = new GroupData().withID(id). withName(name);
      groups.add(new GroupData().withID(id). withName(name));//добавляем созданный объект в список
    }
    return groups;
  }

  //метод возвращает не список, а уже готовое множество
  public Set<GroupData> all() {
    Set<GroupData> groups = new HashSet<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group")); //выбираем список всех элементов с css-селектором span.group
    for (WebElement element : elements) { //переменная element пробегает по списку elements
      String name = element.getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //ищем элемент внутри элемента
      // GroupData group = new GroupData().withID(id). withName(name);
      groups.add(new GroupData().withID(id). withName(name));//добавляем созданный объект в множество
    }
    return groups;
  }


}
