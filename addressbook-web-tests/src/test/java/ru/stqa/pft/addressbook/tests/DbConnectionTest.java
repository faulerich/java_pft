package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

/**
 * Created by Bond on 23.06.2017.
 */
public class DbConnectionTest {

  @Test
  public void testDbConnection() {
    Connection conn = null;
    try {
      //коннектимся к БД
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
      Groups groups = new Groups();
      while (rs.next()) {
        groups.add(new GroupData().withID(rs.getInt("group_id")).withName(rs.getString("group_name"))
                .withHeader(rs.getString("group_header")).withFooter((rs.getString("group_footer"))));
      }

      //закрываем соединение с БД
      rs.close();
      st.close();
      conn.close();

      //выводим результат запроса на экран
      System.out.println(groups);

      // Do something with the Connection

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
