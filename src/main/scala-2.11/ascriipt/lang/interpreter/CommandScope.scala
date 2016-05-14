package ascriipt.lang.interpreter

import ascriipt.lang.common.{Command, CommandSignature}

class CommandScope private(commandsBySignature: Map[CommandSignature, Command]) {

    def add(commands: Command*): CommandScope = {
        val newEntries = commands.map(cmd => cmd.signature -> cmd)
        new CommandScope(commandsBySignature ++ newEntries)
    }

    def resolve(signature: CommandSignature): Option[Command] = {
        commandsBySignature.get(signature)
    }

    def call(signature: CommandSignature, arguments: Seq[Any]) = {
        resolve(signature).get.call(arguments)
    }

    def handles(signature: CommandSignature): Boolean = resolve(signature).isDefined

    def commands: Seq[Command] = commandsBySignature.values.toSeq

    def ++(that: CommandScope) = CommandScope(this.commands ++ that.commands)
}

object CommandScope {
    def empty: CommandScope = new CommandScope(Map[CommandSignature, Command]())

    def apply(commands: Seq[Command]): CommandScope = {
        empty.add(commands: _*)
    }

}
