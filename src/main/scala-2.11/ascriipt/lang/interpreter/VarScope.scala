package ascriipt.lang.interpreter

trait VarScope {
    def resolveVar(varName: String): Any
    def assign(varName: String, value: Any): VarScope
    def newChildScope = ChildScope(this)
}
