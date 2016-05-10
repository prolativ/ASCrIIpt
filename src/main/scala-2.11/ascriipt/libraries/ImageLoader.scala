package ascriipt.libraries

import ascriipt.animation.model.AsciiImage
import ascriipt.common.AscriiptException
import com.sksamuel.scrimage.Image
import java.io.File
import java.nio.file.NoSuchFileException

object PixelToAsciiMapper {

}

class ImageLoader(val path: String) {
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

  def map(number: Int): Char = if (number == -1)'\0' else '|'

  def toAsciiImage: AsciiImage = {
    new AsciiImage(
      Array.tabulate[Char](image.height, image.width) {
        (i, j) => map(image.pixel(j, i).toInt)
    })
  }
}
