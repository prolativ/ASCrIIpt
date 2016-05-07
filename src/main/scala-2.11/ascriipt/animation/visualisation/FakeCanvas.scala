package ascriipt.animation.visualisation

import ascriipt.animation.model.{AnimationTimeException, ExactDuration, Animation}

class FakeCanvas extends Canvas{
    override def drawChar(x: Int, y: Int, char: Char): Unit = {}
}

object FakeCanvas {
    def drawAnimation(animation: Animation): Unit = {
        implicit val canvas = new FakeCanvas

        animation.baseDuration match {
            case ExactDuration(totalDuration) => {
                val timeStep = 100
                (0L until totalDuration by timeStep).foreach { atTime =>
                    animation.draw(atTime, totalDuration)
                }
            }
            case _ => throw AnimationTimeException("Root animation has to have exact duration given.")
        }
    }
}
