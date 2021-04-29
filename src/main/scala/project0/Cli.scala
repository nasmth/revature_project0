package project0

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import scala.collection.mutable.Map

/** CLI class, used to communicate with the user
  */
class Cli {

  //I'm going to use a little regex to make our lives easier
  // good to look into!
  // we're going to extract commands and argument(s) from user input
  val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r

  /** runs the main menu, on a loop
    */
  def menu(): Unit = {
    printTitle()
    printMenuOptions()
    var continueMenuLoop = true
    while (continueMenuLoop) {
      //we can get user input via StdIn.  I'm going to grab a line at a time:
      // this is blocking, so program flow will not continue until input is received
      var input = StdIn.readLine()
      input match {
        case commandArgPattern(cmd, arg) if cmd == "contacts" => {
          DAO.printContacts()
        }
        case commandArgPattern(cmd, arg) if cmd == "messages" => {
          DAO.printMessages()
        }
        case commandArgPattern(cmd, arg) if cmd == "edit" => {
          DAO.editMessage()
        }
        case commandArgPattern(cmd, arg) if cmd == "new" && arg == "contact" => {
          DAO.newContact()
        }
        case commandArgPattern(cmd, arg) if cmd == "new" && arg == "message" => {
          DAO.newMessage()
        }
        case commandArgPattern(cmd, arg) if cmd == "delete" && arg == "contact" => {
          DAO.deleteContact()
        }
        case commandArgPattern(cmd, arg) if cmd == "delete" && arg == "message" => {
          DAO.deleteMessage()
        }
        case commandArgPattern(cmd, arg) if cmd == "import" => {
          DAO.importContact(arg)
        }
        case commandArgPattern(cmd, arg) if cmd == "help" => {
          printMenuOptions()
        }
        case commandArgPattern(cmd, arg) if cmd == "exit" => {
          continueMenuLoop = false
        }
        case commandArgPattern(cmd, arg) => {
          println(
            s"Parsed command $cmd with args $arg did not correspond to an option"
          )
        }
        case _ => {
          //default case if no other cases are matched
          println("Failed to parse command.")
        }
      }

    }

  }

  def printTitle(): Unit = {
    println("Messaging system engaged")
  }

  def printMenuOptions(): Unit = {
    List(
      "Menu options:",
      "contacts: list all contacts ID, Name, and Phone",
      "messages: list all messages",
      "edit: edit a message",
      "new contact: create a new contact",
      "new message: send a new message",
      "delete contact: delete a contact and all associated messages",
      "delete message: delete a message",
      "import: adds a contact from the specified file",
      "exit: exits"
    ).foreach(println)

  }

}
