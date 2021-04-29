package project0

import java.io.FileNotFoundException
import scala.io.StdIn

object DAO {
    def printContacts(): Unit ={
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("SELECT * FROM contacts")
    stmt.execute()
    val rs = stmt.getResultSet()
    while(rs.next) {
      println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3))
    }
    conn.close()
  }

      def printMessages(): Unit ={
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("SELECT * FROM messages")
    stmt.execute()
    val rs = stmt.getResultSet()
    while(rs.next) {
      println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3))
    }
    conn.close()
  }

  def editMessage(): Unit ={
    println("Which message would you like to edit?")
    printMessages()
    val messageId = StdIn.readInt()
    println("Write the new message:")
    val newMessage = StdIn.readLine()
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("UPDATE messages SET message = ? WHERE id = ?;")
    stmt.setString(1, newMessage)
    stmt.setInt(2, messageId)
    stmt.execute()
    println("Message edited.")
    conn.close()
  }

    def newContact(): Unit ={
    println("What is the new contact's name?")
    val newName = StdIn.readLine()
    println("What is the new contact's number?")
    val newNumber = StdIn.readLine()
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("INSERT INTO contacts (name, phone) VALUES (?, ?)")
    stmt.setString(1, newName)
    stmt.setString(2, newNumber)
    stmt.execute()
    println("Contact created.")
    conn.close()
  }

    def newMessage(): Unit ={
    println("Which contact do you want to write to?")
    printContacts()
    val recipientId = StdIn.readInt()
    println("Write the message below:")
    val newMessage = StdIn.readLine()
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("INSERT INTO messages (phone_fk, message) VALUES ((SELECT phone FROM contacts WHERE id = ?), ?)")
    stmt.setInt(1, recipientId)
    stmt.setString(2, newMessage)
    stmt.execute()
    println("Message sent.")
    conn.close()
  }

    def deleteContact(): Unit ={
    println("What contact would you like to delete?")
    printContacts()
    val contactId = StdIn.readInt()
    val contactId2 = contactId
    println("This will delete all associated messages. Are you sure? y/n")
    val confirmation = StdIn.readLine()
    if (confirmation != "y") {
        println("Process aborted.")
        return
    }
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("DELETE FROM messages where phone_fk = (SELECT phone FROM contacts WHERE id = ?);" +
      "DELETE FROM contacts WHERE id = ?;")
    stmt.setInt(1, contactId)
    stmt.setInt(2, contactId)
    stmt.execute()
    println("Contact deleted.")
    conn.close()
  }

    def deleteMessage(): Unit ={
    println("Which message would you like to delete?")
    printMessages()
    val messageId = StdIn.readInt()
    println("Delete message? y/n")
    val confirmation = StdIn.readLine()
    if (confirmation != "y") {
        println("Process aborted.")
        return
    }
    val conn = ConnectionUtil.getConnection()
    val stmt = conn.prepareStatement("DELETE FROM messages WHERE id = ?;")
    stmt.setInt(1, messageId)
    stmt.execute()
    println("Message deleted.")
    conn.close()
  }

  def importContact(filename : String): Unit = {
      try {
         val jsonString = os.read(os.pwd/filename)
         val data = ujson.read(jsonString)
         val conn = ConnectionUtil.getConnection()
         val stmt = conn.prepareStatement("INSERT INTO contacts (name, phone) VALUES (?,?);")
         stmt.setString(1, data("name").str)
         stmt.setString(2, data("phone").str)
         stmt.executeUpdate()
         println("Imported contact from " + filename)
         conn.close()
      }
      catch {
          case fnfe: FileNotFoundException => {
              println(s"File not found: ${fnfe.getMessage()}")
          }
      }
  }
}
