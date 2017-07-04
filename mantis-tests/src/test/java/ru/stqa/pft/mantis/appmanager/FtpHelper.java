package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Bond on 05.07.2017.
 */
public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  //инициализация ftp-клиента
  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  //метод загружает новый файл и при этом временно переименовывает старый
  public void upload(File file, String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup); //удаляем на всякий случай резервную копию файла
    ftp.rename(target, backup); //переименовываем удаленный файл (делаем бекап)
    ftp.enterLocalPassiveMode();
    ftp.storeFile(target, new FileInputStream(file)); //передача файла на удаленную машину (там он будет называться target)
    ftp.disconnect(); //разрыв соединения
  }

  //метод восстанавливает старый файл
  public void restore(String backup, String target) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup, target);
    ftp.disconnect();
  }
}
