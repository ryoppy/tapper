package com.ryoppy.tapper

import org.scalatest.{FunSpec, MustMatchers}

import scala.util.{Failure, Success}

class AllSpec extends FunSpec with MustMatchers {

  class ExpectedException extends Exception

  describe("Tap") {
    it("unsafeTap <| ◁") {
      intercept[ExpectedException] {
        1.unsafeTap(throw new ExpectedException)
      }
      intercept[ExpectedException] {
        1 <| (throw new ExpectedException)
      }
      intercept[ExpectedException] {
        1 ◁ (throw new ExpectedException)
      }
    }
  }

  describe("BooleanTap") {
    it("tapTrue") {
      intercept[ExpectedException] {
        true.tapTrue(throw new ExpectedException)
      }
      false.tapTrue(fail())
    }
    it("tapFalse") {
      intercept[ExpectedException] {
        false.tapFalse(throw new ExpectedException)
      }
      true.tapFalse(fail())
    }
  }

  describe("TryTap") {
    it("tapSuccess") {
      intercept[ExpectedException] {
        Success(1).tapSuccess(_ => throw new ExpectedException)
      }
      Failure[Int](new Exception).tapSuccess(_ => fail())
    }
    it("tapFailure") {
      intercept[ExpectedException] {
        Failure[Int](new Exception).tapFailure(_ => throw new ExpectedException)
      }
      Success(1).tapFailure(x => fail())
    }
  }

  describe("OptionTap") {
    it("tapSome") {
      intercept[ExpectedException] {
        Some(1).tapSome(_ => throw new ExpectedException)
      }
      (None: Option[Int]).tapSome(_ => fail())
    }
    it("tapNone") {
      intercept[ExpectedException] {
        (None: Option[Int]).tapNone(throw new ExpectedException)
      }
      Some(1).tapNone(fail())
    }
  }

  describe("EitherTap") {
    it("tapRight") {
      intercept[ExpectedException] {
        Right(1).tapRight(_ => throw new ExpectedException)
      }
      Left[Int,Int](1).tapRight(_ => fail())
    }
    it("tapLeft") {
      intercept[ExpectedException] {
        Left(1).tapLeft(_ => throw new ExpectedException)
      }
      Right[Int,Int](1).tapLeft(x => fail())
    }
  }
}
