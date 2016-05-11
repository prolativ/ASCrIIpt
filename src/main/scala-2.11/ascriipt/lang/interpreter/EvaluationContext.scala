package ascriipt.lang.interpreter

import ascriipt.lang.ast.CommandSignature

case class EvaluationContext(varScope: VarScope/*, commandScope: CommandScope*/) {
    def resolveVar(varName: String): Any = varScope.resolveVar(varName)
    def assign(varName: String, value: Any): EvaluationContext = {
        this.copy(varScope = varScope.assign(varName, value))
    }
    /*def define(signature: CommandSignature, operation: EvaluationContext => Any): EvaluationContext = {
        commandScope.define(signature, operation)
    }
    def call(signature: CommandSignature, args: Seq[Any])(context: EvaluationContext) = commandScope.call(signature)*/
    def childContext = EvaluationContext(ChildScope(varScope)/*, commandScope*/)
}
