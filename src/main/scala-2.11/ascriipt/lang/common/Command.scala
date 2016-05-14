package ascriipt.lang.common

case class Command(signature: CommandSignature, operation: Seq[Any] => Any) {
    def call(arguments: Seq[Any]): Any = {
        operation(arguments)
    }
}
