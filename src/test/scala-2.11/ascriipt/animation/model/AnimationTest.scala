package ascriipt.animation.model

import org.scalatest.{Matchers, FlatSpecLike}
import Matchers._

class AnimationTest extends FlatSpecLike {

  behavior of "ParallelAnimation"
  it should "last ExactDuration(10)" in {
    ParallelAnimation(Seq(TimedWaiting(10))).baseDuration shouldEqual ExactDuration(10)
  }

  it should "last ExactDuration(10) with other shorter animations" in {
    ParallelAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    )).baseDuration shouldEqual ExactDuration(10)
  }

  it should "throw AnimationTimeException when longer animation in provided sequence" in {
    intercept[AnimationTimeException] {
      ParallelAnimation(Seq(
        TimedWaiting(10),
        ParallelAnimation(Seq(
          TimedWaiting(20),
          AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
        )),
        TimedWaiting(20)
      )).baseDuration shouldEqual ExactDuration(10)
    }
  }

  it should "expand shorter animation of MinimalDuration to the length of outer animation" in {
      ParallelAnimation(Seq(
        TimedWaiting(10),
        SequentialAnimation(Seq(
          TimedWaiting(8),
          AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
        ))
      )).baseDuration shouldEqual ExactDuration(10)
    }

  behavior of "SequentialAnimation"
//  it should "asdzxc" in {
//    SequentialAnimation(Seq(
//      TimedWaiting(10),
//      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c'),
//      UntimedWaiting //todo <- is possible to have more than one MinimalDuration(..) in SequentialAnimation?
//    )).baseDuration shouldEqual MinimalDuration(10)
//  }

  it should "last MinimalDuration(10) when exists at least one animation with unspecified duration" in {
    SequentialAnimation(Seq(
      TimedWaiting(10),
      AsciiPointFixedDistanceMovement(0, 0, 1, 1, 'c')
    )).baseDuration shouldEqual MinimalDuration(10)
  }

  it should "be ExactDuration(10) when all animations have specified duration" in {
    SequentialAnimation(Seq(
      TimedWaiting(10)
    )).baseDuration shouldEqual ExactDuration(10)
  }

  it should "last MinimalDuration(0) when all animations have unspecified duration" in {
    SequentialAnimation(Seq(
      UntimedWaiting,
      UntimedWaiting
    )).baseDuration shouldEqual MinimalDuration(0)
  }

  behavior of "compound animation"
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




  it should "minimal duration" in {
    SequentialAnimation(Seq(TimedWaiting(10))).baseDuration shouldEqual ExactDuration(10)
  }

}
