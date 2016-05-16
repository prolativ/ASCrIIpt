package ascriipt.lang.predef

import ascriipt.animation.model._
import ascriipt.lang.common._
import SignatureHelpers._

object AnimationCommands {
  def commands: Seq[Command] = Seq(
    command("wait"){ case Seq() => UntimedWaiting },
    command("wait", __){ case Seq(time: Int) => TimedWaiting(time) },
    command("par", __){ case Seq(animations: Seq[Animation]) => ParallelAnimation(animations) },
    command("seq", __){ case Seq(animations: Seq[Animation]) => SequentialAnimation(animations) },
    command("show", "point", __, "at", __, __){
      case Seq(filling: String, x: Int, y: Int) if filling.length == 1 => AsciiPoint(x, y, filling.head)
    }
  )
}
