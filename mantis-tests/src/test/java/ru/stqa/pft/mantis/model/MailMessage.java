package ru.stqa.pft.mantis.model;

/**
 * Created by Bond on 05.07.2017.
 */
public class MailMessage {  //модельный объект

  public String to; //кому пришло письмо
  public String text; //текст письма

  public MailMessage(String to, String text) {
    this.to = to;
    this.text = text;
  }
}
