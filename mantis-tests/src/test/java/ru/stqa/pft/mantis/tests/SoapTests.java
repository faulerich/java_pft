package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Bond on 09.07.2017.
 */
public class SoapTests extends TestBase{

  @Test //обращение к багтрекеру через удаленный программный интерфейс (Remote API) и получение информации
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    System.out.println("Проектов: " + projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test //создаем баг-репорт в мантис
  public void teetCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description").withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    assertEquals(issue.getSummary(), created.getSummary());

  }
}
