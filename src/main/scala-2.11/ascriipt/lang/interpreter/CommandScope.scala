package ascriipt.lang.interpreter

import ascriipt.lang.ast.CommandSignature

case class CommandScope(patterns: Map[CommandSignature, Seq[Any] => Any]) {
    /*def define(
                  signature: CommandSignature,
                  operation: EvaluationContext => Any
                  ): Map[CommandSignature, (EvaluationContext) => Any] = {
        patterns ++ Map(signature -> operation)
    }*/

    def call(signature: CommandSignature, arguments: Seq[Any]) = {
        patterns(signature)(arguments)
    }

    def handles(signature: CommandSignature): Boolean = patterns.isDefinedAt(signature)
}

object CommandScope {
    def empty = CommandScope(Map[CommandSignature, Seq[Any] => Any]())
}
