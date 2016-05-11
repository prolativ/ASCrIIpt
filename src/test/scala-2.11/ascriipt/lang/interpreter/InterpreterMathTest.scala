package ascriipt.lang.interpreter

import ascriipt.lang.ast.{Addition, IntConst}
import org.scalatest.{Matchers, FlatSpecLike}
import Matchers._

class InterpreterMathTest extends FlatSpecLike {
    behavior of "Interpreter"

    val interpreter = new BasicInterpreter
    import interpreter.eval

    trait EmptyScopeContextFixture {
        implicit val varScope = RootScope(Map())
    }

    it should "add 2 integer constants" in new EmptyScopeContextFixture{
        eval(Addition(IntConst(3), IntConst(4))) shouldEqual 7
    }
}
