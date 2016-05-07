package ascriipt.lang.interpreter

trait Scope {
    def resolveVar(varName: String): Any
    def assign(varName: String, value: Any): Scope
}
