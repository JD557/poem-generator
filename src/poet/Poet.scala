package poet

import scala.io.Source

class Poet(val filename:String,val degreeVerse:Int,val degreeStrophe:Int) {
  
  lazy val lines = Source.fromFile(filename).getLines.toList.map(_.split(" ").toList)
  lazy val wordChain = lines.map(list => buildMarkovChain(list.reverse,degreeVerse)).reduce(_.union(_))
  lazy val endings = splitStrophes(lines.map(_.last))
  lazy val endingChain = endings.map(strophe => buildMarkovChain(strophe,degreeStrophe)).reduce(_.union(_))
  
  def getSubstrings(line:List[String],length:Integer):List[List[String]] = 
    if (line.length <= length) List(line)
    else (line.take(length) :: getSubstrings(line.tail,length))
    
  def buildMarkovChain(line:List[String],degree:Integer):MarkovChain[String] = {
    val possibleStrings =
      getSubstrings(line, degree+1) ++ ((1 until (degree+1)).map(line.take(_)))
    val dist =
      possibleStrings.foldLeft(new Distribution[String]())(_.insert(_))
    new MarkovChain().insertDistribution(dist)
  }

  def splitStrophes(lines:List[String]):List[List[String]] = lines match {
    case Nil => Nil
    case "" :: xs => splitStrophes(xs)
    case x :: xs => lines.takeWhile(_.length()>0) :: splitStrophes(lines.dropWhile(_.length()>0))
  }
  
  def generateEndings(num:Integer,prec:List[String]):List[String] = {
    val newPrec = prec.takeRight(degreeStrophe);
    if (num<=0) Nil
    else endingChain.getRandomSuccessor(newPrec) match {
      case Some(succ) => succ :: generateEndings(num-1,newPrec ++ List(succ))
      case None => Nil
    }
  }
  
  def generateSentence(prec:List[String]):List[String] = {
    val newPrec = prec.takeRight(degreeVerse);
    wordChain.getRandomSuccessor(newPrec) match {
      case Some(word) => word :: generateSentence(newPrec ++ List(word))
      case None => Nil
    }
  }
  
  def generateStrophe(num:Integer): String =
    generateEndings(num,Nil)
    .map(end => generateSentence(end :: Nil).reverse.mkString(" ")+" "+end)
    .mkString("\n")

}