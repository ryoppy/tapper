package com.ryoppy

import scala.util.{Try, Failure}

package object tapper {

  implicit final class Tap[A](val self: A) extends AnyVal {
    def unsafeTap(f: A => Any): A = { f(self); self }
    def <|(f: A => Any): A = unsafeTap(f)
    def ◁(f: A => Any): A = unsafeTap(f)
  }

  implicit final class BooleanTap(val self: Boolean) extends AnyVal {
    def tapTrue(f: => Boolean): Boolean = { if (self) f; self }
    def tapFalse(f: => Boolean): Boolean = { if (!self) f; self }
  }

  implicit final class TryTap[A](val self: Try[A]) extends AnyVal {
    def tapSuccess(f: A => Any): Try[A] = {
      self.foreach(f)
      self
    }
    def tapFailure(f: Throwable => Any): Try[A] = {
      self match { case Failure(e: Throwable) => f(e) case _ => }
      self
    }
  }

  implicit final class OptionTap[A](val self: Option[A]) extends AnyVal {
    def tapSome(f: A => Any): Option[A] = {
      self.foreach(f)
      self
    }
    def tapNone(f: => Any): Option[A] = {
      if (self.isEmpty) f
      self
    }
  }

  implicit final class EitherTap[A, B](val self: Either[A, B]) extends AnyVal {
    def tapRight(f: B => Any): Either[A, B] = {
      self.right.foreach(f)
      self
    }

    def tapLeft(f: A => Any): Either[A, B] = {
      self.left.foreach(f)
      self
    }
  }
}
