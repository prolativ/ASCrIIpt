package ascriipt.lang.interpreter

import java.io.File

import ascriipt.lang.common.Command
import ascriipt.lang.predef.{GeomCommands, AnimationCommands, MathCommands}

object LangStdDependencyResolver extends DependencyResolver {
  override def commandsByModuleId(moduleName: String, currentFile: File): Seq[Command] = moduleName match {
    case "math" => MathCommands.commands
    case "geom" => GeomCommands.commands
    case _ => Seq()
  }

  override val implicitlyAvailableCommands: Seq[Command] = {
    Seq(
      AnimationCommands.commands
    ).flatten
  }
}
