package ascriipt.lang.interpreter

import java.io.{FileReader, File}

import ascriipt.lang.ast.ModuleBody
import ascriipt.lang.common.Command
import ascriipt.lang.parser.AscriiptParser

class NativeDependencyResolver(
                                  parser: => AscriiptParser,
                                  evaluator: => AscriiptEvaluator,
                                  basePath: String
                                  ) extends DependencyResolver {
    private val baseFile = new File(basePath).getCanonicalFile
    private val parentFile = baseFile.getParentFile

    override def commandsByModuleId(moduleId: String): Seq[Command] = {
        val pathPieces = moduleId.split("/")
        val modulePath = parentFile.getPath + File.separator + pathPieces.mkString(File.separator) + ".aspt"
        commandsFromModuleFile(new File(modulePath))
    }

    override def implicitlyAvailableCommands: Seq[Command] = Seq.empty

    def baseModuleCommands: Seq[Command] = commandsFromModuleFile(baseFile)

    private def commandsFromModuleFile(file: File): Seq[Command] = {
        if(file.isFile) {
            val input = new FileReader(file)
            val parseResult = parser.parseModule(input)
            parseResult match {
                case parser.Success(moduleBody: ModuleBody, in) =>
                    evaluator.evalExternal(moduleBody) match {
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
