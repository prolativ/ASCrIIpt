package ascriipt.lang.interpreter

import java.io.File
import java.net.URLClassLoader

import ascriipt.animation.model.Animation
import ascriipt.animation.visualisation.{RenderHook, FakeScreen}
import com.google.common.reflect.ClassPath

import scala.util.Try

object AscriiptApp extends App {

  args match {
    case Array("eval", expressionStr: String) =>
      val file = new File(System.getProperty("user.dir"))
      evalExpression(expressionStr, file)

    case Array("eval", "-f", basePath: String, expressionStr: String) =>
      val file = new File(basePath)
      evalExpression(expressionStr, file)

    case _ =>
      println(s"ASCrIIpt cannot handle arguments: ${args.mkString(", ")}")
  }

  private def evalExpression(expressionStr: String, file: File) = {
    val interpreter = new BasicInterpreter
    interpreter.eval(expressionStr, file) match {
      case animation: Animation =>
        println(animation)
        // -> retrieving render hooks
        val dir = new File("src/main/resources/classes")
        val files = if(dir.exists && dir.isDirectory) {
          dir.listFiles.foreach(x => println(x.getName))
          dir.listFiles.filter(_.isFile).toList
        }
        else {
          List[File]()
        }

        val cl = new URLClassLoader(files.map(_.toURI.toURL).toArray, this.getClass.getClassLoader)

        import scala.collection.JavaConverters._
        val s = ClassPath.from(cl).getTopLevelClasses("ascriipt.animation.visualisation").asScala

        val renderHooks = s.map(_.load())
          .map(x => Try(x.newInstance()))
          .filter(_.isSuccess)
          .map(_.get)
          .filter(_.isInstanceOf[RenderHook])
          .map(_.asInstanceOf[RenderHook])
        // <- retrieving render hooks
        FakeScreen.drawAnimation(animation, renderHooks)
      case result =>
        println(result)
    }
  }
}
