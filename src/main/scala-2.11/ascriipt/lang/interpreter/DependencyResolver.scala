package ascriipt.lang.interpreter

import java.io.File

import ascriipt.lang.common.Command

trait DependencyResolver {
  def commandsByModuleId(moduleId: String, currentFile: File): Seq[Command]

  def implicitlyAvailableCommands: Seq[Command]
}
