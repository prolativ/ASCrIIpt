package ascriipt.animation.visualisation

trait Screen extends Canvas{
  def clear(): Unit
  def show(): Unit
  def getArray: Array[Array[Char]]
}