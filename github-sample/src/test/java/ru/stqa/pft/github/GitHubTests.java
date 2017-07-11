package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Bond on 12.07.2017.
 */
public class GitHubTests {

  @Test
  public void testCommits() throws IOException {

    //создаем новый github-объект со сгенерированным предварительно на сайте github токеном
    Github github = new RtGithub("825758f7cb8de96f972488f96bc2b192d07a1148");
    //передаем имя пользователя на гитхабе и название репозитория
    RepoCommits commits = github.repos().get(new Coordinates.Simple("faulerich", "java_pft")).commits();

    //пройдем по полученным коммитам
    for (RepoCommit commit:commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }

  }
}
