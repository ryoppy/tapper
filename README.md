# Tapper

Tapper is tap method utils of Scala.

## Example1

just tap.

```
// not cool
def run(): String = {
  val r = something()
  logger.info("executed something. " + r)
  r
}

// cool!
def run(): String = {
  something() <| logger.info("executed something. " + _)
}
```

## Example2

Try tap.

```
// not cool
def write(): Try[Result] = { 
  val t = Try(db.write(something))
  t match {
    case Failure(e: Throwable) => logger.warn("db error", e)
    case _ =>
  }
  t
}

// cool!
def write(): Try[Result] = {
  Try(db.write(something))
    .tapFailure(logger.warn("db error", _))
}
```

### Usage

imports.

```
import com.ryoppy.tapper.Tap

1 <| println
```

```
import com.ryoppy.tapper.TryTap

Try(1) tapSuccess println
```

or all imports.

```
import com.ryoppy.tapper._
```

Try, Option, Boolean, Either, anymore...

Please see [this unit test](./src/test/scala/com/ryoppy/tapper/AllSpec.scala).
