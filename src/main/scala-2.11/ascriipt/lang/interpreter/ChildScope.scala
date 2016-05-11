package ascriipt.lang.interpreter

case class ChildScope(bindings: Map[String, Any], parentScope: VarScope) extends VarScope{
    override def resolveVar(varName: String): Any = {
        bindings.getOrElse(varName, parentScope.resolveVar(varName))
    }

    override def assign(varName: String, value: Any): VarScope = {
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

object ChildScope {
    def apply(parentScope: VarScope): ChildScope = ChildScope(Map[String, Any](), parentScope)
}
