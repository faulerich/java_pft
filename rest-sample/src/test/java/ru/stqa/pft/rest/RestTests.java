package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Bond on 11.07.2017.
 */
public class RestTests extends TestBase {

  @Test (enabled = true) //создание баг-репорта в баг-трекере Bugify
  public void testCreateIssue() throws IOException {

    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("Test issue description").withState_name("Open");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {

    //авторизуемся и отправляем запрос
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues.json"))
            .returnContent().asString();

    /* распарсиваем json-ответ от сервера */
    //получаем json-элемент
    JsonElement parsed = new JsonParser().parse(json);

    //извлекаем из него по ключу нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    //преобразуем полученный элемент в множество объектов типа Issue
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private int createIssue(Issue newIssue) throws IOException { //для создания тикета отправляем POST-запрос с параметрами
    String json = getExecutor().execute(Request.Post("http://demo.bugify.com/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription()),
                    new BasicNameValuePair("state_name", newIssue.getState_name())))
            .returnContent().asString();
    //получаем json-элемент (анализируем строку)
    JsonElement parsed = new JsonParser().parse(json);
    //берем значение по ключу (см. ответ в интерфейсе API) Это будет ID созданного баг-репорта
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  @Test
  public void test_SampleIssue7 () throws IOException {
    skipIfNotFixed(7);
    System.out.println("Some Test Steps...");
  }
}
