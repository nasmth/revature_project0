package project0

import java.sql.DriverManager
import java.sql.Connection

object ConnectionUtil {
  def getConnection() : Connection = {
      classOf[org.postgresql.Driver].newInstance()
      val conn = DriverManager.getConnection(
          "jdbc:postgresql://127.0.0.1:5026/contacts",
          "postgres",
          "fancy"
      )
      conn
  }
}
