package ascriipt.animation.model

import ascriipt.animation.visualisation.Canvas

trait Animation {
    def baseDuration: AnimationDuration
    def draw(atTime: Long, totalDuration: Long)(implicit canvas: Canvas)
}
