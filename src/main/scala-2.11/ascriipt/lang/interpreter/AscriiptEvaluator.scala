package ascriipt.lang.interpreter

import java.io.File

import ascriipt.lang.ast.AstNode

trait AscriiptEvaluator {
  def eval(astNode: AstNode)(implicit currentFile: File, varScope: VarScope, staticCommandScope: CommandScope): Any

  def evalInBasicContext(astNode: AstNode, currentFile: File): Any
}
