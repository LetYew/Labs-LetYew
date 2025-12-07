package Lab2

import scala.util.Random
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

//Intel Core i3-1005G1 2 физических ядра и 4 потока
//Это для определения числа потоков
def piMonteCarlo(points:Int, threads:Int): Float = {
  val pointsPerThread: Int = (points/threads) + 1                                                                        //Кол-во точек на поток

  //Здесь создаем фигуры
  //NOTE: Круг сделан типом object
  Circle.setRadius(2)
  val radius: Float = Circle.getRadius
  val squareSide: Float = radius

  //Генератор чисел
  //Сделал так, чтобы создавать его один раз в коде, не делать каждый раз новый
  val generator = new Random()

  //Массив потоков
  val threadsArray = new Array[Thread](threads)

  //Тут мы потоки создаем и запускаем
  //2) Стильно и с reduce
  val results = (0 until threads)
    .map(_ => Future {
      (1 to pointsPerThread).count { _ =>
        checkIsConcyclic(randomPointGenerator(generator, squareSide), radius)
      }
    }(ExecutionContext.global))
    .map(f => Await.result(f, Duration.Inf))
    .sum

    //Кронк, тяни рычаг!!!(1)
    

  //Считаем ответ по формуле и его же возвращаем
  val answerPI: Float = 4*(results.toFloat/points.toFloat)
  /*душа просит сюда return*/answerPI
}


//Генератор точки в пределах квадрата
def randomPointGenerator(generator: scala.util.Random, squareSide: Float): (Float,Float) =
{
  //Случайные значения от 0 до радиуса круга
  val X: Float = (generator.nextFloat() * squareSide)
  val Y: Float = (generator.nextFloat() * squareSide)
  (X,Y)
}


//Лежит ли точка в круге???
def checkIsConcyclic(point :(Float,Float), radius: Float): Boolean = {
  val answer: Boolean = if((point._1*point._1) + (point._2*point._2) < (radius*radius)) true else false
  answer
}


//Это старая версия через циклы, маловато фп, страшно, рот шатал
def piMonteCarloCrappy(points:Int, threads:Int): Float = {
  val pointsPerThread: Int = (points/threads) + 1                                                                        //Кол-во точек на поток

  //Здесь создаем фигуры
  //NOTE: Круг сделан типом object
  Circle.setRadius(2)
  val radius: Float = Circle.getRadius
  val squareSide: Float = radius

  //Генератор чисел
  //Сделал так, чтобы создавать его один раз в коде, не делать каждый раз новый
  val generator = new Random()

  //Массив потоков
  val threadsArray = new Array[Thread](threads)

  //Тут мы потоки создаем и запускаем

  //1) Слабенько но работает но вообще фу
  var results: Int = 0

  for(i <- 0 until threads) {

    val thread = new Thread(() => {
      for(j <- 1 to pointsPerThread) {
        val newPointCoordinates:(Float, Float) = randomPointGenerator(generator, squareSide)
        if (checkIsConcyclic(newPointCoordinates, radius)) {
          results += 1
        }
      }

    })
  
    //Кронк, тяни рычаг!!!(1)
    threadsArray(i) = thread
    threadsArray(i).start()
  }

  //Ждем завершения работы потоков
  threadsArray.foreach(_.join())

  //Считаем ответ по формуле и его же возвращаем
  val answerPI: Float = 4*(results.toFloat/points.toFloat)
  /*душа просит сюда return*/answerPI
}