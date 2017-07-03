package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bond on 04.07.2017.
 */
public class HttpSession {

  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }

  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //формируем post-запрос (запрос с параметрами)

    //формируем параметры
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));

    //упаковываем параметры и помещаем в созданный запрос
    post.setEntity(new UrlEncodedFormEntity(params));
    CloseableHttpResponse response = httpclient.execute(post); //отправка запроса
    String body = geTextFrom(response); //получаем текст ответа (html-текст)

    //проверяем, действительно ли пользователь успешно вошел
    //признак этого - код страницы содержит строку <span class="user-info">%s</span>
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
  }

  private String geTextFrom(CloseableHttpResponse response) throws IOException {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  //определяем, какой пользователь сейчас залогинен
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php"); //отправка get-запроса (параметры не передаются)
    CloseableHttpResponse response = httpclient.execute(get);
    String body = geTextFrom(response);
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
  }

}
