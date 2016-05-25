package ascriipt.lang.interpreter

import java.io.{FileReader, File}

import ascriipt.lang.ast.ModuleBody
import ascriipt.lang.common.Command
import ascriipt.lang.parser.AscriiptParser

class NativeDependencyResolver(
                                  parser: => AscriiptParser,
                                  evaluator: => AscriiptEvaluator
                                  ) extends DependencyResolver {

  override def commandsByModuleId(moduleId: String, currentFile: File): Seq[Command] = {
    val baseDirectory = if(currentFile.isFile){
      currentFile.getParentFile
    } else {
      currentFile
    }
    val moduleFile = moduleId.split("/").foldLeft(baseDirectory)(new File(_, _)).getCanonicalFile
    commandsFromModuleFile(moduleFile)
  }

  override def implicitlyAvailableCommands: Seq[Command] = Seq.empty

  def commandsFromModuleFile(moduleFile: File): Seq[Command] = {
    if(moduleFile.isFile) {
      val input = new FileReader(moduleFile)
      val parseResult = parser.parseModule(input)
      parseResult match {
        case parser.Success(moduleBody: ModuleBody, in) =>
          evaluator.evalInBasicContext(moduleBody, moduleFile) match {
            case ascriiptModule: AscriiptModule =>
              ascriiptModule
                  .definedCommandScope
                  .commands
          }
      }
    } else {
      Seq()
    }
  }
}
