package ascriipt.lang.predef

import ascriipt.animation.model._
import ascriipt.lang.common._
import SignatureHelpers._

object AnimationCommands {
  def commands: Seq[Command] = Seq(
    command("move", __, "by", __, __){
      case Seq(animation: Animation, dx: Int, dy: Int) => MovementByVector(animation, dx, dy)
    },
    command("par", __){ case Seq(animations: Seq[Animation]) => ParallelAnimation(animations) },
    command("seq", __){ case Seq(animations: Seq[Animation]) => SequentialAnimation(animations) },
    command("wait"){ case Seq() => UntimedWaiting },
    command("wait", __){ case Seq(time: Int) => TimedWaiting(time) }
  )
}
