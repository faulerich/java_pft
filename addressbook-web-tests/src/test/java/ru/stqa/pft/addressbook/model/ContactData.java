package ru.stqa.pft.addressbook.model;

public class ContactData {
  private int id;  //модификатор final убрали, т.к. с ним мы не сгенерируем setter
  private final String firstname;
  private final String lastname;
  private final String homephone;
  private final String email;
  private final String birthyear;
  private final String group;

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
  }

  @Override
  public int hashCode() {
    int result = firstname != null ? firstname.hashCode() : 0;
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  public void setId(int id) {
    this.id = id;
  }

  //если не получаем id в качестве параметра, то вызывается этот ContactData
  public ContactData(String firstname, String lastname, String homephone, String email, String birthyear, String group) {
    this.id = Integer.MAX_VALUE; //чтобы контакт при сортировке оказался на первом месте
    this.firstname = firstname;
    this.lastname = lastname;
    this.homephone = homephone;
    this.email = email;
    this.birthyear = birthyear;
    this.group = group;
  }

  //если получаем id в качестве параметра, то вызывается этот ContactData
  public ContactData(int id, String firstname, String lastname, String homephone, String email, String birthyear, String group) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.homephone = homephone;
    this.email = email;
    this.birthyear = birthyear;
    this.group = group;
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

  public String getGroup() {
    return group;
  }
}
