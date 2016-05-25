package ascriipt.lang.interpreter

import java.io.File

import ascriipt.lang.common.Command

class GeneralDependencyResolver(
                                   subresolvers: DependencyResolver*
                                   ) extends DependencyResolver {
  override def commandsByModuleId(moduleId: String, currentFile: File): Seq[Command] = {
    subresolvers
        .map(_.commandsByModuleId(moduleId, currentFile))
        .find(_.nonEmpty)
        .getOrElse(throw ModuleNotResolvedException(moduleId))
  }

  override def implicitlyAvailableCommands: Seq[Command] = {
    subresolvers.flatMap(_.implicitlyAvailableCommands)
  }
}
