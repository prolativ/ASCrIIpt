package ascriipt.lang.predef

import ascriipt.animation.model.{AsciiPoint, Triangle, Rect}
import ascriipt.lang.common.Command
import ascriipt.lang.common.SignatureHelpers._

object GeomCommands {
  def commands: Seq[Command] = Seq(
    command("show", "point", __, "at", __, __){
      case Seq(filling: String, x: Int, y: Int) if filling.length == 1 => AsciiPoint(x, y, filling.head)
    },
    command("show", "rect", __, __, "of", __, "left", __, "top", __){
      case Seq(width: Int, height: Int, filling: String, x: Int, y: Int) if filling.length == 1 =>
        Rect(width, height, x, y, filling.head)
    },
    command("show", "triangle", "of", __, "spanned", "by", __, __, __, __, __, __){
      case Seq(filling: String, x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int) if filling.length == 1 =>
        Triangle(x1, y1, x2, y2, x3, y3, filling.head)
    }
  )
}
