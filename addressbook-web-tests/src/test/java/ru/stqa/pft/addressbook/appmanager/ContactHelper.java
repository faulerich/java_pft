package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bond on 22.05.2017.
 */
public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void goToContactList() {

    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void fillContactCreationForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("home"), contactData.getHomephone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("byear"), contactData.getBirthyear());


    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void deleteSelectedContacts() {

    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectElement(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectElementById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModification(int index) {
    click(By.xpath("//a[@href='edit.php?id=" + index + "']"));
  }


  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contactData, boolean b) {
    initContactCreation();
    fillContactCreationForm(contactData, b);
    submitContactCreation();
    contactCache = null;
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();     //получаем размер списка элементов
  }


  public void modify(ContactData contact) {
    fillContactCreationForm(contact, false);
    submitContactModification();
    contactCache = null;
    goToContactList();
  }

  public void delete(int index) {
    selectElement(index);
    deleteSelectedContacts();
    goToContactList();
  }

  public void delete(ContactData contact) {
    selectElementById(contact.getId());
    deleteSelectedContacts();
    contactCache = null;
    goToContactList();
  }

  //метод для получения списка контактов
  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry")); //выбираем список всех элементов
    for (WebElement element : elements) { //переменная element пробегает по списку elements
      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //ищем элемент внутри элемента
      ContactData contact = new ContactData().withID(id).withFirstName(firstname).withLastName(lastname);
      contacts.add(contact); //добавляем созданный объект в список
    }
    return contacts;
  }

  private Contacts contactCache = null;

  //метод для получения множества контактов
  public Contacts all() {

    if (contactCache != null) {
      return new Contacts(contactCache);
    }

    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry")); //выбираем список всех элементов
    for (WebElement element : elements) { //переменная element пробегает по списку elements
      String lastname = element.findElement(By.xpath(".//td[2]")).getText();
      String firstname = element.findElement(By.xpath(".//td[3]")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //ищем элемент внутри элемента
      ContactData contact = new ContactData().withID(id).withFirstName(firstname).withLastName(lastname);
      contactCache.add(contact); //добавляем созданный объект в множество
    }
    return contactCache;
  }


}
