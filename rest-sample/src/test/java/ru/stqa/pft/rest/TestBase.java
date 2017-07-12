package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Bond on 12.07.2017.
 */
public class TestBase {

  private Object issueType;
  private String issueStatus;
  private JsonElement testdata;

  public boolean isIssueOpened(int issueId) throws IOException {
    String status = issueStatus(issueId);
    if (!status.equals("Closed")) {
      return true;
    }
    return false;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpened(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId + " was not fixed");
    }
  }

  public Executor getExecutor() {
    //авторизуемся на сервере api
    return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==", "");
  }

  //статус тикета по id
  public String issueStatus(int id) throws IOException {
    //авторизуемся и отправляем запрос на один тикет
    String json = getExecutor().execute(Request.Get("http://demo.bugify.com/api/issues/" + id + ".json"))
            .returnContent().asString();
    //узнаем статус тикета

    /* распарсиваем json-ответ от сервера */
    //получаем json-элемент
    JsonElement parsed = new JsonParser().parse(json);

    //извлекаем из него по ключу нужную часть
    JsonElement issues = parsed.getAsJsonObject().get("issues");

    //преобразуем полученный элемент в множество объектов типа Issue
    Set<Issue> issue = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    issueStatus = issue.iterator().next().getState_name();
    //issueStatus = issue.iterator().next().getDescription();
    return issueStatus;
  }
}
