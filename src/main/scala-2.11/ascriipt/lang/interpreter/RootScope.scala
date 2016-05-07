package ascriipt.lang.interpreter

case class RootScope(bindings: Map[String, Any]) extends Scope {
    override def resolveVar(varName: String): Any = {
        bindings.getOrElse(varName, throw VariableNotResolvedException(varName, this))
    }

    override def assign(varName: String, value: Any): Scope = {
        if(bindings.isDefinedAt(varName)) {
            throw DoubleBindingException(varName, this)
        } else {
            RootScope(bindings + (varName -> value))
        }
    }
}
