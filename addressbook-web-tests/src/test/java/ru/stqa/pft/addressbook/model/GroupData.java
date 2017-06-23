package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@XStreamAlias("group")
@Entity
@Table(name = "group_list") //название таблицы

public class GroupData {
  @Expose
  @Column(name = "group_name") //название столбца в таблице
  private String name;
  @XStreamOmitField
  @Id
  @Column(name = "group_id") //название столбца в таблице
  private int id = Integer.MAX_VALUE;;  //модификатор final убрали, т.к. с ним мы не сгенерируем setter
  @Expose
  @Column(name = "group_header") //название столбца в таблице
  @Type(type = "text") //дополнительно указываем тип
  private String header;
  @Expose
  @Column(name = "group_footer") //название столбца в таблице
  @Type(type = "text") //дополнительно указываем тип
  private String footer;


  public int getId() {
    return id;
  }

  public GroupData withID(int id) {
    this.id = id;
    return this;
  }

  public GroupData withName(String name) {
    this.name = name;
    return this;
  }

  public GroupData withHeader(String header) {
    this.header = header;
    return this;
  }

  public GroupData withFooter(String footer) {
    this.footer = footer;
    return this;
  }

  public String getName() {
    return name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }


  @Override
  public String toString() {
    return "GroupData{" +
            "id='" + id + '\'' +
            "name='" + name + '\'' +
            "header='" + header + '\'' +
            "footer='" + footer + '\'' +
            '}';
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupData groupData = (GroupData) o;

    if (id != groupData.id) return false;
    return name != null ? name.equals(groupData.name) : groupData.name == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + id;
    return result;
  }
}