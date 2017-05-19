package ru.stqa.pft.addressbook;

public class ContactData {
  private final String firstname;
  private final String lastname;
  private final String homephone;
  private final String email;
  private final String birthyear;

  public ContactData(String firstname, String lastname, String homephone, String email, String birthyear) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.homephone = homephone;
    this.email = email;
    this.birthyear = birthyear;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getHomephone() {
    return homephone;
  }

  public String getEmail() {
    return email;
  }

  public String getBirthyear() {
    return birthyear;
  }
}
