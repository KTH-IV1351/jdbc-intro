package se.kth.iv1351.jdbcintro;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A small program that illustrates how to write a simple JDBC program.
 */
public class BasicJdbc {
  private static final String TABLE_NAME = "person";
  private PreparedStatement createPersonStmt;
  private PreparedStatement findAllPersonsStmt;
  private PreparedStatement deletePersonStmt;

  private void accessDB() {
    try (Connection connection = createConnection()) {
      createTable(connection);
      prepareStatements(connection);
      createPersonStmt.setString(1, "stina");
      createPersonStmt.setString(2, "0123456789");
      createPersonStmt.setInt(3, 43);
      createPersonStmt.executeUpdate();
      createPersonStmt.setString(1, "olle");
      createPersonStmt.setString(2, "9876543210");
      createPersonStmt.setInt(3, 12);
      createPersonStmt.executeUpdate();
      listAllRows();
      deletePersonStmt.setString(1, "stina");
      deletePersonStmt.executeUpdate();
      listAllRows();
    } catch (SQLException | ClassNotFoundException exc) {
      exc.printStackTrace();
    }
  }

  private Connection createConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/simplejdbc",
      "postgres", "postgres");
    // Class.forName("com.mysql.cj.jdbc.Driver");
    // return DriverManager.getConnection(
    // "jdbc:mysql://localhost:3306/simplejdbc?serverTimezone=UTC",
    // "root", "javajava");
  }

  private void createTable(Connection connection) {
    try (Statement stmt = connection.createStatement()) {
      if (!tableExists(connection)) {
        stmt.executeUpdate(
            "create table " + TABLE_NAME + " (name varchar(32) primary key, phone varchar(12), age int)");
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  private boolean tableExists(Connection connection) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet tableMetaData = metaData.getTables(null, null, null, null);
    while (tableMetaData.next()) {
      String tableName = tableMetaData.getString(3);
      if (tableName.equalsIgnoreCase(TABLE_NAME)) {
        return true;
      }
    }
    return false;
  }

  private void listAllRows() {
    try (ResultSet persons = findAllPersonsStmt.executeQuery()) {
      while (persons.next()) {
        System.out.println(
            "name: " + persons.getString(1) + ", phone: " + persons.getString(2) + ", age: " + persons.getInt(3));
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  private void prepareStatements(Connection connection) throws SQLException {
    createPersonStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)");
    findAllPersonsStmt = connection.prepareStatement("SELECT * from " + TABLE_NAME);
    deletePersonStmt = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE name = ?");
  }

  public static void main(String[] args) {
    new BasicJdbc().accessDB();
  }
}
