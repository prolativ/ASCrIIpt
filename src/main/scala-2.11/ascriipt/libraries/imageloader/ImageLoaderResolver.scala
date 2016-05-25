package ascriipt.libraries.imageloader

import java.io.File

import ascriipt.lang.common.Command
import ascriipt.lang.common.SignatureHelpers._
import ascriipt.lang.interpreter.{FileResolver, DependencyResolver}

object ImageLoaderResolver extends DependencyResolver{
  override def commandsByModuleId(moduleId: String, currentFile: File): Seq[Command] = moduleId match {
    case "image" => Seq(
      command("show", "image", __, "left", __, "top", __){
        case Seq(imagePath: String, leftX: Int, topY: Int) =>
          val imageFile = FileResolver.resolveFile(currentFile.getParentFile.getAbsoluteFile, imagePath)
          val imageLoader = new ImageLoader(imageFile)
        imageLoader.toAsciiImage(leftX, topY)
      },
      command("show", "image", "with", "scale", __, "left", __, "top", __){
        case Seq(imagePath: String, leftX: Int, topY: Int) =>
          val imageFile = FileResolver.resolveFile(currentFile.getParentFile.getAbsoluteFile, imagePath)
          val imageLoader = new ImageLoader(imageFile)
          imageLoader.toAsciiImageWithScale(leftX, topY)
      }
    )

    case _ => Seq()
  }

  override def implicitlyAvailableCommands: Seq[Command] = Seq()
}
