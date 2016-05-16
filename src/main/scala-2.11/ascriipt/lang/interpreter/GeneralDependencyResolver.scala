package ascriipt.lang.interpreter

import ascriipt.lang.common.Command

class GeneralDependencyResolver(
                                   subresolvers: DependencyResolver*
                                   ) extends DependencyResolver {
  override def commandsByModuleId(moduleId: String): Seq[Command] = {
    subresolvers
        .map(_.commandsByModuleId(moduleId))
        .find(_.nonEmpty)
        .getOrElse(throw ModuleNotResolvedException(moduleId))
  }

  override def implicitlyAvailableCommands: Seq[Command] = {
    subresolvers.flatMap(_.implicitlyAvailableCommands)
  }
}
