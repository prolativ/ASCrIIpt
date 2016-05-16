package ascriipt.lang.common

sealed trait SignatureElement

case class PatternWord(value: String) extends SignatureElement

sealed trait ArgumentSlot extends SignatureElement

case object UntypedArgumentSlot extends ArgumentSlot

case class CommandSignature(signatureElements: Seq[SignatureElement]) {
  def patternString: String = {
    val patternWords = signatureElements.map {
      case PatternWord(word) => word
      case UntypedArgumentSlot => "_"
    }
    patternWords.mkString(" ")
  }

  override def toString = s"CommandSignature($patternString)"
}

object SignatureHelpers {
  implicit def StringToPatternWord(s: String) = PatternWord(s)
  implicit val __ = UntypedArgumentSlot
  def command(elements: SignatureElement*)(operation: Seq[Any] => Any): Command = {
    Command(CommandSignature(elements), operation)
  }
}