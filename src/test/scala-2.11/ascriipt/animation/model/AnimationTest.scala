package ascriipt.animation.model

import org.scalatest.{Matchers, FlatSpecLike}
import Matchers._

class AnimationTest extends FlatSpecLike {

  it should "exact duration" in {
    ParallelAnimation(Seq(TimedWaiting(10))).baseDuration shouldEqual ExactDuration(10)
  }

  it should "work properly" in {
    ParallelAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    )).baseDuration shouldEqual ExactDuration(10)
  }

  it should "asdzxc" in {
    SequentialAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    )).baseDuration shouldEqual MinimalDuration(10)
  }

  it should "return ExactDuration(26)" in {
    val sequence = SequentialAnimation(Seq(
      TimedWaiting(10),
      ParallelAnimation(Seq(
        TimedWaiting(3),
        AsciiPointFixedDistanceMovement(0, 0, 4, 4, 'O')
      )),
      TimedWaiting(10),
      ParallelAnimation(Seq(
        TimedWaiting(3),
        AsciiPointFixedDistanceMovement(4, 4, 0, 0, 'O')
      ))
    ))

    sequence.baseDuration shouldEqual ExactDuration(26)
  }
  it should "Zxc" in {
    val seq = SequentialAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    ))

    val par = ParallelAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    ))

    ParallelAnimation(Seq(seq, par)).baseDuration shouldEqual ExactDuration(10)
    SequentialAnimation(Seq(seq, par)).baseDuration shouldEqual MinimalDuration(20)

  }

  it should "throw exception" in {
    intercept[AnimationTimeException] {
      ParallelAnimation(Seq(
        TimedWaiting(10),
        AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c'),
        TimedWaiting(20)
      )).baseDuration
    }
  }


  it should "minimal duration" in {
    SequentialAnimation(Seq(TimedWaiting(10))).baseDuration shouldEqual ExactDuration(10)
  }

}
