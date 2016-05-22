package ascriipt.lang.common

import ascriipt.lang.interpreter.LocalCommandScope

sealed trait AbstractCommand {
  def signature: CommandSignature
}

case class Command(signature: CommandSignature, operation: Seq[Any] => Any) extends AbstractCommand {
  def call(arguments: Seq[Any]): Any = {
    operation(arguments)
  }

  def toLocalCommand: LocalCommand = {
    LocalCommand(
      signature,
      _ => operation
    )
  }

  override def toString = s"Command(${signature.patternString})"
}

case class LocalCommand(signature: CommandSignature, operation: LocalCommandScope => Seq[Any] => Any) extends AbstractCommand {
  def call(arguments: Seq[Any])(localCommandScope: LocalCommandScope): Any = {
    operation(localCommandScope)(arguments)
  }

  def toAbsoluteCommand(localCommandScope: LocalCommandScope): Command = {
    Command(
      signature,
      operation(localCommandScope)
    )
  }

  override def toString = s"LocalCommand(${signature.patternString})"
}
