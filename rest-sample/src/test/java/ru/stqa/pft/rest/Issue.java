package ru.stqa.pft.rest;

/**
 * Created by Bond on 11.07.2017.
 */
public class Issue {

  private int id;
  private String subject;
  private String description;

  public String getState_name() {
    return state_name;
  }

  public Issue withState_name(String state_name) {
    this.state_name = state_name;
    return this;
  }

  private String state_name;

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Issue issue = (Issue) o;

    if (id != issue.id) return false;
    if (subject != null ? !subject.equals(issue.subject) : issue.subject != null) return false;
    if (description != null ? !description.equals(issue.description) : issue.description != null) return false;
    return state_name != null ? state_name.equals(issue.state_name) : issue.state_name == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (subject != null ? subject.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (state_name != null ? state_name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            ", state_name='" + state_name + '\'' +
            '}';
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setState_name(String state_name) {
    this.state_name = state_name;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;

  }
}
