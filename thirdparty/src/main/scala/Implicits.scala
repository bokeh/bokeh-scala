package io.continuum.bokeh
package thirdparty

import scala.reflect.ClassTag
import scala.collection.immutable.NumericRange

import org.joda.time.{DateTime,LocalTime=>Time,LocalDate=>Date}
import breeze.linalg.{DenseVector,DenseMatrix}

import play.api.libs.json.{Json,Writes}

trait Implicits {
    implicit def DenseVectorWrites[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) = Json.toJson(vec.toArray)
    }

    implicit val DateTimeJSON = new Writes[DateTime] {
        def writes(datetime: DateTime) = Json.toJson(datetime.getMillis)
    }

    implicit val TimeJSON = new Writes[Time] {
        def writes(time: Time) = Json.toJson(time.getMillisOfDay)
    }

    implicit val DateJSON = new Writes[Date] {
        def writes(date: Date) = Json.toJson(date.toDateTimeAtStartOfDay)
    }

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
