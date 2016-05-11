package ascriipt.lang.interpreter

case class RootScope(bindings: Map[String, Any]) extends VarScope {
    override def resolveVar(varName: String): Any = {
        bindings.getOrElse(varName, throw VariableNotResolvedException(varName, this))
    }

    override def assign(varName: String, value: Any): VarScope = {
        if(bindings.isDefinedAt(varName)) {
            throw DoubleBindingException(varName, this)
        } else {
            RootScope(bindings + (varName -> value))
        }
    }
}

object RootScope {
    def empty = RootScope(Map[String, Any]())
}
