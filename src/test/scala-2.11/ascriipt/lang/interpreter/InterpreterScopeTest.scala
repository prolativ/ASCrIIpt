package ascriipt.lang.interpreter

import ascriipt.lang.ast.Variable
import org.scalatest.{Matchers, FlatSpecLike}
import Matchers._

class InterpreterScopeTest extends FlatSpecLike {
    behavior of "Interpreter"

    val interpreter = new BasicInterpreter
    import interpreter.eval

    it should "get a variable from root scope" in {
        implicit val varScope = RootScope(Map("a" -> 3))
        eval(Variable("a")) shouldEqual 3
    }

    it should "get a variable defined only in child scope" in {
        val rootScope = RootScope(Map())
        val childScope = ChildScope(Map("a" -> 10), rootScope)
        eval(Variable("a"))(childScope) shouldEqual 10
    }

    it should "get an eclipsed variable from child scope" in {
        val rootScope = RootScope(Map("a" -> 3))
        val childScope = ChildScope(Map("a" -> 10), rootScope)
        eval(Variable("a"))(childScope) shouldEqual 10
    }



}
