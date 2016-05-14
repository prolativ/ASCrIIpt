package ascriipt.lang.interpreter

import ascriipt.lang.common.Command
import ascriipt.lang.predef.{AnimationCommands, MathCommands}

object LangStdDependencyResolver extends DependencyResolver {
    override def commandsByModuleId(moduleName: String): Seq[Command] = moduleName match {
        case "math" => MathCommands.commands
        case _ => Seq()
    }

    override val implicitlyAvailableCommands: Seq[Command] = {
        Seq(
            AnimationCommands.commands
        ).flatten
    }
}
