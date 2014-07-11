package poet

import scala.io._

object Main {
  
  def genStrophe(args: Array[String]):Option[String] = args match {
    case Array() => None
    case Array(filename) => Some(new Poet(filename,2,1).generateStrophe(4))
    case Array(filename,verses) => Some(new Poet(filename,2,1).generateStrophe(verses.toInt))
  }
  
  def main(args: Array[String]) = genStrophe(args) match {
      case None => println("Usage: poet <filename> [verses]")
      case Some(output) => println(output)
    }
  
}