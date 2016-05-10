package ascriipt.libraries

import ascriipt.animation.model.AsciiImage
import ascriipt.common.AscriiptException
import com.sksamuel.scrimage.Image
import java.io.File
import java.nio.file.NoSuchFileException

class ImageLoader(var path: String) {
  protected val image: Image = loadImage(path)

  protected def loadImage(path: String): Image = {
    try {
      Image.fromFile(new File(path))
    } catch {
      case e: NoSuchFileException => throw new AscriiptException {
        override def message: String = "File " + path + " not found."
      }
    }
  }

  def toAsciiImage: AsciiImage = {
    val imageToAscii = Array.tabulate[Char](image.width, image.height) {
      (i, j) => if (image.pixel(i, j).toInt == -1) '\0' else '|'
    }
    new AsciiImage(imageToAscii)
  }
}
