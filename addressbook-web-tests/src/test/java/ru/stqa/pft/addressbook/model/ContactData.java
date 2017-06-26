package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="addressbook")
@XStreamAlias("contact")

public class ContactData {
  @XStreamOmitField
  @Id
  @Column(name="id")
  private int id = Integer.MAX_VALUE;  //модификатор final убрали, т.к. с ним мы не сгенерируем setter
  @Expose
  @Column(name="firstname")
  private String firstname;
  @Expose
  @Column(name="lastname")
  private String lastname;

  @Expose

  @Column(name="home")
  @Type(type = "text")
  private String homephone;
  @Column(name="work")
  @Type(type = "text")
  private String workphone;
  @Column(name="mobile")
  @Type(type = "text")
  private String mobilephone;
  @Transient //означает, что пока не используется
  private String firstemail;
  @Transient //означает, что пока не используется
  private String secondemail;
  @Transient //означает, что пока не используется
  private String thirdemail;
  @Type(type = "text")
  private String email;

  @Transient //означает, что пока не используется
  private String birthyear;

  @Transient //означает, что пока не используется
  private String allPhones;
  @Transient //означает, что пока не используется
  private String allEmails;
  @Type(type = "text")
  private String address;

  @Column(name="photo")

  @Type(type = "text")
  private String photo;

  //аннотация "многие-ко-многим" (группа может содержать много контактов и контакт может входить в много групп)
  @ManyToMany  (fetch = FetchType.EAGER) //за одиин "заход" из БД извлекается как можно больше информации
  //в качестве связующей таблицы используется "address_in_groups",
  // joinColumns - объект, который указывает на столбец текущего класса (на контакты), т.е. столбец id
  // inverseJoinColumns - объект, который указывает на столбец другого класса (на группы), т.е. столбец group_id
  @JoinTable (name = "address_in_groups", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>(); //инициализируем свойство (т.е. создаем пустое множество типа GroupData)

  public File getPhoto() {
    return new File(photo);
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }


  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (homephone != null ? !homephone.equals(that.homephone) : that.homephone != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    return address != null ? address.equals(that.address) : that.address == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (homephone != null ? homephone.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", homephone='" + homephone + '\'' +
            ", email='" + email + '\'' +
            ", address='" + address + '\'' +
            '}';
  }

  public ContactData withID(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstName(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withLastName(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withHomephone(String homephone) {
    this.homephone = homephone;
    return this;
  }

  public ContactData withMobilephone(String mobilephone) {
    this.mobilephone = mobilephone;
    return this;
  }

  public ContactData withWorkphone(String workphone) {
    this.workphone = workphone;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withFirstEmail(String firstemail) {
    this.firstemail = firstemail;
    return this;
  }

  public ContactData withSecondEmail(String secondemail) {
    this.secondemail = secondemail;
    return this;
  }

  public ContactData withThirdEmail(String thirdemail) {
    this.thirdemail = thirdemail;
    return this;
  }

  public ContactData withBirthyear(String birthyear) {
    this.birthyear = birthyear;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
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

  public String getMobilephone() {
    return mobilephone;
  }

  public String getWorkphone() {
    return workphone;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstEmail() {
    return firstemail;
  }

  public String getSecondEmail() {
    return secondemail;
  }

  public String getThirdEmail() {
    return thirdemail;
  }

  public String getBirthyear() {
    return birthyear;
  }

  public String getFullAddress() {
    return address;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }
}
