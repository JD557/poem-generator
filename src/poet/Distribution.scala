package poet

class Distribution[T](private val occur:Map[List[T],Int],val count:Int) {
  
  
  def this() = this(Map(),0)
  
  val occurMap = occur.withDefaultValue(0)
  
  val isEmpty = count == 0
  
  def insert(item:List[T]):Distribution[T] = 
	new Distribution(occurMap.updated(item,occurMap(item)+1),count+1)
	
  def get(occur:List[T]):Double = occurMap(occur)/count.toDouble
  
  def union(that:Distribution[T]):Distribution[T] = {
    def updateMap(accum:Map[List[T],Int],value:(List[T],Int)):Map[List[T],Int] = {
      val (entry,num) = value;
      accum + (entry -> (accum(entry) + num))
    }
    val unionMap:Map[List[T],Int] = this.occurMap.foldLeft(that.occurMap)(updateMap)
    new Distribution(unionMap,this.count + that.count)
  }
  
  override def toString():String =
    occurMap.map{case (list,count) =>
      "|{"+list.mkString(", ")+"}|"+count+"|"}
    .mkString("\n")
}