package poet

import scala.io._

object worksheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val p = new Poet("asd",4)                       //> p  : poet.Poet = poet.Poet@39de2ea4
  val sub = p.getSubstrings(List("um","do","li","ta","do","li","pum"), 3)
                                                  //> sub  : List[List[String]] = List(List(um, do, li), List(do, li, ta), List(li
                                                  //| , ta, do), List(ta, do, li), List(do, li, pum))
  val dist = sub.foldLeft(new Distribution[String]())(_.insert(_))
                                                  //> dist  : poet.Distribution[String] = |{do, li, ta}|1|
                                                  //| |{ta, do, li}|1|
                                                  //| |{li, ta, do}|1|
                                                  //| |{um, do, li}|1|
                                                  //| |{do, li, pum}|1|
 
  val chain = new MarkovChain().insertDistribution(dist)
                                                  //> chain  : poet.MarkovChain[String] = |{do, li}|ta|1|
                                                  //| |{do, li}|pum|1|
                                                  //| |{ta, do}|li|1|
                                                  //| |{li, ta}|do|1|
                                                  //| |{um, do}|li|1|
                                                  
  chain.insert(List("do","li"),"ta",1)            //> res0: poet.MarkovChain[String] = |{do, li}|ta|2|
                                                  //| |{do, li}|pum|1|
                                                  //| |{ta, do}|li|1|
                                                  //| |{li, ta}|do|1|
                                                  //| |{um, do}|li|1|
                                                  
  chain union (chain.insert(List("do","li"),"ta",1))
                                                  //> res1: poet.MarkovChain[String] = |{do, li}|ta|3|
                                                  //| |{do, li}|pum|2|
                                                  //| |{ta, do}|li|2|
                                                  //| |{li, ta}|do|2|
                                                  //| |{um, do}|li|2|
                                                  
  chain.getRandomSuccessor("do"::"li"::Nil)       //> res2: Option[String] = Some(pum)
}