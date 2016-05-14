package ascriipt.lang.interpreter

import ascriipt.lang.common.Command

trait DependencyResolver {
    def commandsByModuleId(moduleId: String): Seq[Command]
    def implicitlyAvailableCommands: Seq[Command]
}
