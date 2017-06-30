package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

/**
 * Created by Bond on 24.06.2017.
 */
public class DbHelper {
  private final SessionFactory sessionFactory;

  public DbHelper() {
// A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

  }

  public Groups groups() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();

    return new Groups(result);
  }

  public Contacts contacts() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
    session.getTransaction().commit();
    session.close();

    return new Contacts(result);
  }

  public ContactData getContactFromDb(int id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    ContactData result = (ContactData) session.createQuery("from ContactData where id=" + id).getSingleResult();
    session.getTransaction().commit();
    session.close();
    return result;
  }

  public GroupData getGroupFromDb(int id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    GroupData result = (GroupData) session.createQuery("from GroupData where id=" + id).getSingleResult();
    session.getTransaction().commit();
    session.close();
    return result;
  }

  //выбираем группу с максимальным id из существующих
  public GroupData getGroupWithMaxIDFromDb() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    GroupData result = (GroupData) session.createQuery("from GroupData where id = (select max(group1.id) from GroupData group1)").getSingleResult();
    session.getTransaction().commit();
    session.close();
    return result;
  }

  public Groups getGroupsOfContactFromDb(int id) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from ContactData where group_id=" + id).list();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  // получим подходящую для добавления группу

  public GroupData situatedGroup(Groups groups, ContactData contact) {
    Groups situatedGroups = contact.getGroups(); //получили все группы, в которые входит переданный в метод контакт
    for (GroupData group : groups) {
      if (situatedGroups.contains(group)) {
        continue;
      } else {   //если среди групп контакта нет очередной взятой из общего списка групп, то эта группа - наш клиент
        return group;
      }
    }
    return null;
  }

}
