package ascriipt.libraries.imageloader

import ascriipt.animation.model.AsciiImage
import com.sksamuel.scrimage.Image
import java.io.File
import java.nio.file.NoSuchFileException

import ascriipt.animation.model.AsciiImage

object PixelToAsciiMapper {
  def map(value: String): Char =  value match {
    case "111010010" => 'T'
    case "100010001" => '\\'
    case "001010100" => '/'
    case "111101111" => 'O'
    case "111100111" => 'C'
    case "101010101" => 'X'
    case _ => '\0'
  }
}

class ImageLoader(val file: File) {
  protected val image: Image = loadImage(file)

  protected def loadImage(file: File): Image = {
    try {
      Image.fromFile(file)
    } catch {
      case e: NoSuchFileException => throw new ImageLoadingException(s"File $file not found.")
    }
  }

  def map(number: Int, char: Char = '|'): Char = if (number == -1) '\0' else char

  def scaleMap(x: Int, y: Int) : Char = {
    if (x + 2 > image.width || y + 2 > image.height) return '\0'

    var pattern: String = ""
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        println(x + i + " " + (y + j))
        var tmp = if (image.pixel(x + j, y + i).toInt == -1) "0" else "1"
        pattern += tmp
      }
    }
    PixelToAsciiMapper.map(pattern)
  }

  def toAsciiImage(leftX: Int, topY: Int, char: Char = '|') : AsciiImage = {
    AsciiImage(leftX, topY, Array.tabulate[Char](image.width, image.height) {
        (i, j) => map(image.pixel(i, j).toInt, char)
    })
  }

  def toAsciiImageWithScale(leftX: Int, topY: Int): AsciiImage = {
    AsciiImage(leftX, topY, Array.tabulate[Char](image.width / 3, image.height / 3) {
      (i, j) => scaleMap(3 * i, 3 * j)
    })
  }
}
