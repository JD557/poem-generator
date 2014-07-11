package poet

import scala.util.Random

class MarkovChain[T](val transitions:Map[List[T],Map[T,Int]]) {
  
  def this() = this(Map[List[T],Map[T,Int]]().withDefaultValue(Map()))
  
  def insert(prec:List[T],succ:T,occur:Int):MarkovChain[T] = {
    val oldSucc = transitions(prec).withDefaultValue(0)
    new MarkovChain[T](
      transitions.updated(prec,oldSucc.updated(succ,oldSucc(succ)+occur))
    )
  }
  
  def insert(prec:List[T],next:Map[T,Int]):MarkovChain[T] =
	 next.foldLeft(this){case (accum,(succ,count)) => accum.insert(prec, succ, count)}
    
  def insertDistribution(dist:Distribution[T]):MarkovChain[T] =
    dist.occurMap.foldLeft(this){
    	case (accum,(list,count)) => accum.insert(list.init,list.last,count)
  	}
  
  def getRandomSuccessor(prec:List[T]):Option[T] = {
    def getRandomHelper(list:List[(T,Int)],accum:Int):T = list match {
      case (word,_) :: Nil => word
      case (word,count) :: xs => if (accum<=0) word else getRandomHelper(xs, accum-count)
    }
    val sum = transitions(prec).foldLeft(0){case (accum,(succ,count)) => accum+count};
    if (sum<=0) None
    else Some(getRandomHelper(transitions(prec).toList, Random.nextInt(sum)))
  }
  
  def union(that:MarkovChain[T]):MarkovChain[T] =
    that.transitions.foldLeft(this){
      case (accum,(prec,next)) => accum.insert(prec, next)
  	}
  
  override def toString():String =
    transitions.map({case (prec,next) => 
      next.map({case (succ,count) =>
      	"|{"+prec.mkString(", ")+"}|"+succ+"|"+count+"|"})
      .mkString("\n")})
    .mkString("\n")

}