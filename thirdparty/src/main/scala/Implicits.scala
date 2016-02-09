package io.continuum.bokeh
package thirdparty

import scala.reflect.ClassTag
import scala.collection.immutable.NumericRange

import org.joda.time.{DateTime,LocalTime=>Time,LocalDate=>Date}
import breeze.linalg.{DenseVector,DenseMatrix}

trait ThirdpartySerialization {
    implicit def DenseVectorWriter[T:Json.Writer:ClassTag] = Json.Writer[DenseVector[T]] {
        case vec => Json.writeJs(vec.toArray)
    }

    implicit val DateTimeWriter = Json.Writer[DateTime] {
        case datetime => Json.writeJs(datetime.getMillis)
    }

    implicit val TimeWriter = Json.Writer[Time] {
        case time => Json.writeJs(time.getMillisOfDay)
    }

    implicit val DateWriter = Json.Writer[Date] {
        case date => Json.writeJs(date.toDateTimeAtStartOfDay)
    }
}

trait Implicits {
    implicit object DateTimeDefault extends Default[DateTime](new DateTime)

    implicit object TimeDefault extends Default[Time](new Time)

    implicit object DateDefault extends Default[Date](new Date)

    implicit val DenseVectorArrayLike = new ArrayLike[DenseVector]

    implicit object DenseMatrixToMatrixLike extends MatrixLike[DenseMatrix] {
        def data[A](value: DenseMatrix[A]) = (value.t.toArray, value.rows, value.cols)
    }

    implicit def NumericRangeToDenseVector[T:ClassTag](range: NumericRange[T]) = new DenseVector(range.toArray)
}
object Implicits extends Implicits
