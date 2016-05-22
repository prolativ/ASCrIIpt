package ascriipt.lang.interpreter

import ascriipt.lang.ast.AstNode

trait AscriiptEvaluator {
  def eval(astNode: AstNode)(implicit varScope: VarScope, staticCommandScope: CommandScope): Any

  def evalExternal(astNode: AstNode): Any
}
