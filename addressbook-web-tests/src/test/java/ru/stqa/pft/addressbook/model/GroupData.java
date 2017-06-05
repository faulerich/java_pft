package ru.stqa.pft.addressbook.model;

public class GroupData {
  private final String name;
  private final String id;
  private final String header;
  private final String footer;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GroupData groupData = (GroupData) o;

    if (name != null ? !name.equals(groupData.name) : groupData.name != null) return false;
    return id != null ? id.equals(groupData.id) : groupData.id == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id='" + id + '\'' +
            '}';
  }

  public String getId() {
    return id;
  }

  //если не получаем id в качестве параметра, то вызывается этот GroupData
  public GroupData(String name, String header, String footer) {
    this.id = null;
    this.name = name;
    this.header = header;
    this.footer = footer;
  }

  //если получаем id в качестве параметра, то вызывается этот GroupData
  public GroupData(String id, String name, String header, String footer) {
    this.id = id;
    this.name = name;
    this.header = header;
    this.footer = footer;
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
}
