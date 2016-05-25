package ascriipt.lang.predef

import ascriipt.lang.common.Command
import ascriipt.lang.common.SignatureHelpers._

object MathCommands {
  def commands: Seq[Command] = Seq(
    command(__, "pow", __){ case Seq(x: Int, y: Int) => Math.pow(x, y) }
  )
}
