package ascriipt.lang.predef

import ascriipt.animation.model.{TimedWaiting, UntimedWaiting, AsciiPoint}
import ascriipt.lang.common.{PatternWord, UntypedArgumentSlot, CommandSignature, Command}

object AnimationCommands {
    def commands: Seq[Command] = Seq(
        Command(
            CommandSignature(Seq(PatternWord("point"), UntypedArgumentSlot, UntypedArgumentSlot)),
            { case Seq(x: Int, y: Int) => AsciiPoint(x, y, 'x') }
        ),
        Command(
            CommandSignature(Seq(PatternWord("wait"))),
            { case Seq() => UntimedWaiting }
        ),
        Command(
            CommandSignature(Seq(PatternWord("wait"), UntypedArgumentSlot)),
            { case Seq(time: Int) => TimedWaiting(time) }
        )
    )
}
