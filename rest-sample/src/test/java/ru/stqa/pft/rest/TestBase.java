package ru.stqa.pft.rest;

import org.testng.SkipException;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by Bond on 12.07.2017.
 */
public class TestBase {

  public boolean isIssueOpened(int issueId) throws RemoteException, MalformedURLException {
    //String status =  mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
    String status = app.soap().issueStatus(issueId);
    if (!status.equals("closed")) {
      return true;
    }
    return false;
  }

  public void skipIfNotFixed(int issueId) throws RemoteException, MalformedURLException {
    if (isIssueOpened(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId + "was not fixed");
    }
  }

  public String issueStatus(int id) throws MalformedURLException, RemoteException {
    //открываем соединение
    MantisConnectPortType mc = getMantisConnect();
    //узнаем статус тикета
    Object status = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(id)).getStatus().getName();
    return (String) status;
  }
}
