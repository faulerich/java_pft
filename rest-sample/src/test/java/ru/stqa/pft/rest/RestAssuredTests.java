package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Created by Bond on 11.07.2017.
 */
public class RestAssuredTests {

  @BeforeClass //авторизуемся на сервере API
  public void init() {
    RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
  }

  @Test  //создание баг-репорта в баг-трекере Bugify
  public void testCreateIssue() throws IOException {

    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("Test issue description");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {

    //авторизуемся и отправляем запрос
    String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();


    /* распарсиваем json-ответ от сервера */
    //получаем json-элемент
    JsonElement parsed = new JsonParser().parse(json);

    //извлекаем из него по клюючу нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    //преобразуем полученный элемент в множество объектов типа Issue
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private int createIssue(Issue newIssue) throws IOException { //для создания тикета отправляем POST-запрос с параметрами

    String json = RestAssured.given().parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("http://demo.bugify.com/api/issues.json").asString();

    //получаем json-элемент (анализируем строку)
    JsonElement parsed = new JsonParser().parse(json);
    //берем значение по ключу (см. ответ в интерфейсе API) Это будет ID созданного баг-репорта
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
