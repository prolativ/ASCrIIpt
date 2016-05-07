package ascriipt.lang.interpreter

case class ChildScope(bindings: Map[String, Any], parentScope: Scope) extends Scope{
    override def resolveVar(varName: String): Any = {
        bindings.getOrElse(varName, parentScope.resolveVar(varName))
    }

    override def assign(varName: String, value: Any): Scope = {
        if(bindings.isDefinedAt(varName)) {
            throw DoubleBindingException(varName, this)
        } else {
            ChildScope(
                bindings + (varName -> value),
                parentScope
            )
        }
    }
}
