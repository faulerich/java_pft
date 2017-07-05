package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Bond on 05.07.2017.
 */
public class MailHelper {

  private ApplicationManager app;
  private final Wiser wiser;

  //при инициализации создается объект типа Wiser. Это и есть почтовый сервер
  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser();
  }

  //ожидание прихода почты (count - кол-во ожидаемых писем, timeout - время ожидания)
  public List<MailMessage> waitForMail(int count, long timeout) throws javax.mail.MessagingException, IOException {
    long start = System.currentTimeMillis(); //запоминаем текущее время
    //цикл, проверяющий, что новое текущее время не превышает (момент старта + таймаут)
    while (System.currentTimeMillis() < start + timeout) {
      if (wiser.getMessages().size() >= count) {  //если почты пришло достаточно много, то прекращаем ожидание
        //преобразуем реальные объекты с сервера в модельные объекты
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  //преобразование реальных почтовых сообщений в модельные
  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage(); //берем реальный объект
      //берем список получателей и оставляем только первого из них (он там точно один)
      //и преобразуем письмо в строку, значение которой и попадает в модельный объект
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  //запуск почтового сервера
  public void start() {
    wiser.start();
  }

  //остановка почтового сервера
  public void stop() {
    wiser.stop();
  }
}
