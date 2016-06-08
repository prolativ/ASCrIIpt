package ascriipt.animation.visualisation

import java.io.File
import java.net.URLClassLoader

import ascriipt.animation.model._
import com.google.common.reflect.ClassPath

import scala.util.Try

class FakeScreen(width: Int, height: Int, renderers: scala.collection.mutable.Set[RenderHook]) extends Screen {
  var canvas: Array[Array[Char]] = Array()
  clear()

  override def clear(): Unit = canvas = Array.fill(height, width)(' ')

  override def setChar(point: (Int, Int), char: Char): Unit = {
    val (x, y) = point
    if (x >= 0 && x < width && y >= 0 && y < height) {
      canvas(y)(x) = char
    }
  }

  override def show() = renderers.foreach(_.onRender(this))
  override def getArray = canvas
}

object FakeScreen {
  def main(args: Array[String]) {
    val dir = new File("src/main/resources/classes")
    val files = if(dir.exists && dir.isDirectory) {
      dir.listFiles.foreach(x => println(x.getName))
      dir.listFiles.filter(_.isFile).toList
    }
    else {
      List[File]()
    }

    val cl = new URLClassLoader(files.map(_.toURI.toURL).toArray, this.getClass.getClassLoader)
//    val klazz = cl.loadClass("Renderer")
//    val renderHook = klazz.newInstance().asInstanceOf[RenderHook]

    import scala.collection.JavaConverters._
    val s = ClassPath.from(cl).getTopLevelClasses("ascriipt.animation.visualisation").asScala

    val renderHooks = s.map(_.load())
      .map(x => Try(x.newInstance()))
      .filter(_.isSuccess)
      .map(_.get)
      .filter(_.isInstanceOf[RenderHook])
      .map(_.asInstanceOf[RenderHook])

    val sequence = ParallelAnimation(Seq(
      TimedWaiting(6500),
      SequentialAnimation(Seq(
        ParallelAnimation(Seq(
          TimedWaiting(3000),
          MovementByVector(AsciiPoint(20, 20, 'O'), -20, -20)
        )),
        TimedWaiting(500),
        ParallelAnimation(Seq(
          TimedWaiting(3000),
          MovementByVector(AsciiPoint(0, 0, 'O'), 20, 20)
        ))
      )),
        AsciiPoint(5, 10, 'M'),
        AsciiPoint(40, 24, 'X'),
        AsciiImage(10, 10, Array(Array('z', 'x'), Array('c', 'b'))),
        MovementByVector(AsciiImage(2, 2, Array(Array('z', 'x'), Array('c', 'b'))), 20, 15)
    ))
    drawAnimation(sequence, renderHooks)
  }

  def drawAnimation(animation: Animation, renderers: scala.collection.mutable.Set[RenderHook]): Unit = {
    implicit val canvas = new FakeScreen(60, 25, renderers)

    animation.baseDuration match {
      case ExactDuration(totalDuration) => {
        val timeStep = 100
        (0L until totalDuration by timeStep).foreach { atTime =>
          animation.draw(atTime, totalDuration)
          canvas.show()
          Thread.sleep(timeStep)
          canvas.clear()
        }
      }
      case _ => throw AnimationTimeException("Root animation has to have exact duration given.")
    }
  }
}
