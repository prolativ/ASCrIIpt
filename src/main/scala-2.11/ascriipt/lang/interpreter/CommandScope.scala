package ascriipt.lang.interpreter

import ascriipt.common.AscriiptException
import ascriipt.lang.common.{LocalCommand, AbstractCommand, Command, CommandSignature}

sealed trait AbstractCommandScope[C <: AbstractCommand, Self <: AbstractCommandScope[C, Self]] {
  protected val commandsBySignature: Map[CommandSignature, C] = {
    commands.map(command => command.signature -> command).toMap
  }

  def commands: Seq[C]

  def resolve(signature: CommandSignature): Option[C] = {
    commandsBySignature.get(signature)
  }

  def handles(signature: CommandSignature): Boolean = resolve(signature).isDefined

  def ++(that: Self): Self
}

case class CommandScope(commands: Seq[Command]) extends AbstractCommandScope[Command, CommandScope]{

  if(commands.distinct.length != commands.length){
    throw new AscriiptException("Command name not unique"){}
  }

  def call(signature: CommandSignature, arguments: Seq[Any]) = {
    resolve(signature).get.call(arguments)
  }

  def ++(that: CommandScope): CommandScope = CommandScope(this.commands ++ that.commands)
}

case class LocalCommandScope (commands: Seq[LocalCommand]) extends AbstractCommandScope[LocalCommand, LocalCommandScope]{
  def call(signature: CommandSignature, arguments: Seq[Any])(localCommandScope: LocalCommandScope) = {
    resolve(signature).get.call(arguments)(localCommandScope)
  }

  def ++(that: LocalCommandScope): LocalCommandScope = LocalCommandScope(this.commands ++ that.commands)

  def toCommandScope: CommandScope = {
    CommandScope(commands.map(_.toAbsoluteCommand(this)))
  }
}

object CommandScope {
  def empty: CommandScope = CommandScope(Seq.empty)
}

object LocalCommandScope {
  def empty: LocalCommandScope = LocalCommandScope(Seq.empty)
}
