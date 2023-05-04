package org.pocs

import org.pocs.TaglessFinalExample.ExpressionProblem.Interpreter

object TaglessFinalExample extends App {

  // expression problem
  object ExpressionProblem {
    object Algebra {
      trait Expression
      case class B(boolean: Boolean) extends Expression
      case class Or(left: Expression, right: Expression) extends Expression
      case class And(left: Expression, right: Expression) extends Expression
      case class Not(expression: Expression) extends Expression
      case class I(int: Int) extends Expression
      case class Sum(left: Expression, right: Expression) extends Expression
    }
    trait Interpreter {
      import Algebra._
      // need cast !!!
      def evaluate(expression: Expression): Boolean | Int = expression match {
        case B(b) => b
        case Or(a, b) => evaluate(a).asInstanceOf[Boolean] || evaluate(b).asInstanceOf[Boolean]
        case And(a, b) => evaluate(a).asInstanceOf[Boolean] && evaluate(b).asInstanceOf[Boolean]
        case Not(e) => !evaluate(e).asInstanceOf[Boolean]
        case I(i) => i
        case Sum(a, b) => evaluate(a).asInstanceOf[Int] + evaluate(b).asInstanceOf[Int]
      }
    }
    object Service {
//      import ExpressionProblem.Interpreter._ // or just import it and remove the 2nd param
      import Algebra._ // true, true, false, 24 -3
      def booleanProgram(a: Boolean, b: Boolean, c: Boolean)(i: Interpreter): Any = i.evaluate(Or(B(a), And(B(b), B(c))))
      def intProgram(a: Int, b: Int)(i: Interpreter): Any = i.evaluate(Sum(I(a), I(b)))
    }
    // execution: interpret a program
    def expressionProblem(): Unit = {
      import ExpressionProblem.Interpreter
      import ExpressionProblem.Service._
      println(booleanProgram(true, true, false)(new Interpreter {}))
      println(intProgram(24, -3)(new Interpreter {}))
    }
  }

  // solution 1
  object Tagging {
    object Algebra {
      trait Expression(val tag: String)
      case class B(boolean: Boolean) extends Expression("bool")
      case class Or(left: Expression, right: Expression) extends Expression("bool")
      case class And(left: Expression, right: Expression) extends Expression("bool") {
        assert(left.tag == "bool" && right.tag == "bool")
      }
      case class Not(expression: Expression) extends Expression("bool")
      case class I(int: Int) extends Expression("int")
      case class Sum(left: Expression, right: Expression) extends Expression("int")
    }
    // no type safety, errors shown at runtime, validation at construction time
    trait Interpreter {
      import Algebra._
      def evaluate(expression: Expression): Boolean | Int  = expression match {
        case B(b) => b
        case Or(a, b) =>
          if (a.tag != "bool" || b.tag != "bool")
            throw new IllegalArgumentException("improper argument type")
          else
            evaluate(a).asInstanceOf[Boolean] || evaluate(b).asInstanceOf[Boolean]
        case And(a, b) => evaluate(a).asInstanceOf[Boolean] && evaluate(b).asInstanceOf[Boolean]
      // and so on..
      }
    }
  }

  // solution 2
  object TaglessInitial {

    object Algebra {
      trait Expression[A]
      case class B(boolean: Boolean) extends Expression[Boolean]
      case class Or(left: Expression[Boolean], right: Expression[Boolean]) extends Expression[Boolean]
      case class And(left: Expression[Boolean], right: Expression[Boolean]) extends Expression[Boolean]
      case class Not(expression: Expression[Boolean]) extends Expression[Boolean]
      case class I(int: Int) extends Expression[Int]
      case class Sum(left: Expression[Int], right: Expression[Int]) extends Expression[Int]
    }
    // the problem is that we have an intermediate classes (the case classes above)
    // instead of working directly with the booleans and int
    trait Interpreter {
      import Algebra._
      def evaluate[A](expression: Expression[A]): A = expression match {
        case B(b) => b
        case I(i) => i
        case Or(a, b) => evaluate(a) || evaluate(b)
        case And(a, b) => evaluate(a) && evaluate(b)
        case Not(e) => !evaluate(e)
        case Sum(a, b) => evaluate(a) + evaluate(b)
      }
    }
    object Service {
      //      import Interpreter._ // or just import it and remove the 2nd param
      import Algebra._ // true, true, false, 24 -3
      def booleanProgram(a: Boolean, b: Boolean, c: Boolean)(i: Interpreter): Any = i.evaluate(Or(B(a), And(B(b), B(c))))
      def intProgram(a: Int, b: Int)(i: Interpreter): Any = i.evaluate(Sum(I(a), I(b)))
    }
    // execution: interpret a program
    def taglessInitial(): Unit = {
      import TaglessInitial.Interpreter
      import TaglessInitial.Service._
      println(booleanProgram(true, true, false)(new Interpreter {}))
      println(intProgram(24, -3)(new Interpreter {}))
    }
  }

  // a pattern that solves the expression problem: return the right type for the right expression
  object TaglessFinal {

    object Algebra {
      trait Expression[A] {
        val value: A  // the final value we care about
      }
    }
    trait Dsl {
      import Algebra.Expression
      // evaluation is done instantly
      def b(boolean: Boolean) = new Expression[Boolean] { val value: Boolean = boolean }
      def not(boolean: Boolean) = new Expression[Boolean] { val value: Boolean = !boolean }
      def or (left: Expression[Boolean], right: Expression[Boolean]) = new Expression[Boolean] { val value = left.value || right.value }
      def and (left: Expression[Boolean], right: Expression[Boolean]) = new Expression[Boolean] { val value = left.value && right.value }
      def i(int: Int): Expression[Int] = new Expression[Int] { val value: Int = int }
      def sum (a: Expression[Int], b: Expression[Int]) = new Expression[Int] { val value: Int  = a.value + b.value }
    }
    trait Interpreter {
      import Algebra._
      def evaluate[A](expression: Expression[A]): A = expression.value
    }
    object Service {
      private val dsl = new Dsl {}
      def booleanProgram(a: Boolean, b: Boolean, c: Boolean)(i: Interpreter) = i.evaluate(dsl.or(dsl.b(a), dsl.and(dsl.b(b), dsl.b(c))))
      def intProgram(a: Int, b: Int)(i: Interpreter) = i.evaluate(dsl.sum(dsl.i(a), dsl.i(b)))
    }
    // execution: interpret a program
    def taglessFinal(): Unit = {
      import TaglessFinal.Interpreter
      import TaglessFinal.Service._
      println(booleanProgram(true, true, false)(new Interpreter {}))
      println(intProgram(24, -3)(new Interpreter {}))
    }
  }

  // F[_] : Monad = tagless final
  object TaglessFinalV2 {

    // has the capability of creating expressions of that type
    // an example could be UserLoginAlgebra, with methods checkLogin, lastErrorStatus, mfa_v1, ..., totalSessionsLogging
    trait Algebra[E[_]] {
      def b(boolean: Boolean): E[Boolean]
      def not(boolean: Boolean): E[Boolean]
      def or(left: E[Boolean], right: E[Boolean]): E[Boolean]
      def and(left: E[Boolean], right: E[Boolean]): E[Boolean]
      def i(int: Int): E[Int]
      def sum(a: E[Int], b: E[Int]): E[Int]
    }
    // an example could be UserLoggingStatus
    case class SimpleExpression[A](value: A)
    given simpleAlgebraInterpreter: Algebra[SimpleExpression] with {
      override def b(boolean: Boolean) = SimpleExpression(boolean)
      override def not(boolean: Boolean) = SimpleExpression(!boolean)
      override def or(left: SimpleExpression[Boolean], right: SimpleExpression[Boolean]) = SimpleExpression(left.value || right.value)
      override def and(left: SimpleExpression[Boolean], right: SimpleExpression[Boolean]) = SimpleExpression(left.value && right.value)
      override def i(int: Int) = SimpleExpression(int)
      override def sum(a: SimpleExpression[Int], b: SimpleExpression[Int]) = SimpleExpression(a.value + b.value)
    }
    object Service {
      // example could be userLoginFlow
      def booleanProgram[E[_]](using algebra: Algebra[E]): E[Boolean] = {
        import algebra.*
        or(b(true), and(b(true), b(false)))
      }
      // example could be checkLastStatus
      def intProgram[E[_]](using algebra: Algebra[E]): E[Int] = {
        import algebra.*
        sum(i(24), i(-3))
      }
    }
    // execution: interpret a program
    def taglessFinalV2(): Unit = {
      import TaglessFinalV2.Service._
      println(booleanProgram[SimpleExpression].value)
      println(intProgram[SimpleExpression].value)
    }
  }

  def runExamples(): Unit = {
    import ExpressionProblem.expressionProblem
    println("=== expressionProblem ==+")
    expressionProblem()
    import TaglessInitial.taglessInitial
    println("=== taglessInitial ==+")
    taglessInitial()
    import TaglessFinal.taglessFinal
    println("=== taglessFinal() ==+")
    taglessFinal()
    import TaglessFinalV2.taglessFinalV2
    println("=== taglessFinalV2 ==+")
    taglessFinalV2()
  }

  runExamples()

}
