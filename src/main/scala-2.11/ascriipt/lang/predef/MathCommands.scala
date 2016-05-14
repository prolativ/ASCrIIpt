package ascriipt.lang.predef

import ascriipt.lang.common.{PatternWord, UntypedArgumentSlot, CommandSignature, Command}

object MathCommands {
    def commands: Seq[Command] = Seq(
        Command(
            CommandSignature(Seq(UntypedArgumentSlot, PatternWord("pow"), UntypedArgumentSlot)),
            { case Seq(x: Int, y: Int) => Math.pow(x, y) }
        )
    )
}
