package Lab2


import scala.language.postfixOps
/**<h1>Вариант 2</h1> <br>
 * Вычисление числа Пи методом <b>Монте-Карло</b>: Вам нужна единичная окружность и её описанный квадрат.
 * Далее, действуйте примерно как в варианте 1.<br>
 * <p>
 * <pre><code>Scala: <br>def piMonteCarlo(pointsNumber:Int, threadsNumber:Int):Double</code></pre>
 * </p>
 *
 * <img src = "MCgifDemo.gif">
 *
 * <h1>Решение</h1> <br>
 * Итого три файла: <ul>
 *   <li>Lab2Main.scala - main файл, минимум содержания</li>
 *   <li>MonteCarlo.scala - собственно функция вычислений и пара побочных</li>
 *   <li>Circle.scala - файл под объект круга<i>(я так захотел)</i></li>
 *   </ul>
 *
 *
 *   <h2>Проблемы и вопросы:</h2> <br>
 *   <pre><b>Потоков мало, казна пустеет</b> <br>
 *   Хочется больше потоков, думаю - есть ли смысл пытаться добавить еще
 *   </pre> <br>
 *   <pre><b>Точность смешная</b> <br>
 *   Он ошибается поразительно часто, может - есть смысл увеличить число итераций,
 *   а может я где накосячил, но факт есть факт - <i>точность никудышная</i>
 *   </pre> <br>
 *
 *   <pre><b>А что - фп?</b> <br>
 *   Как будто бы маловато тут на самом деле самого что ни на есть
 *   <b>Функционального программирования</b>
 *   </pre> <br>
*/
@main
def main(): Unit = {

  //Число итераций(генерируемых точек)
  val numIterations: Int = 10000
  println(s"Установленное число точек: ${numIterations}")

  //Число потоков.
  //Почему так мало? Проц на машине = |_Intel Core i3-1005G1 2 физических ядра и 4 потока_|
  val numThreads: Int = 4
  println(s"Выбранное число потоков: $numThreads")

  val answer = piMonteCarlo(numIterations, numThreads)
  println(s"Полученный ответ: $answer")
  val old_answer = piMonteCarloCrappy(numIterations, numThreads)
  println(s"Старым не ФП методом: $old_answer")
}

/**<h1>Scala Enjoyer</h1>
 * <img src = "CliffRider.jpg">
 *
 */
def HelloWorld(): Unit = println("HelloWorld")
















/*
* Junk, Trash etc.

var coordinates: (Float, Float) = (0, 0)
  var sum: Float = 0

  for (i <- 1 to numIterations) {
    println("======================")
    coordinates = randomPointGenerator(generator, squareSide)
    println(s"Generated point is on  X:${coordinates._1} and Y:${coordinates._2}")
    if (checkIsConcyclic(coordinates, Circle.getRadius)) {
      sum += 1
    }
    println("======================")
  }

  println(s"Circle radius currently is ${Circle.getRadius}")
  println(s"Sum is ${sum}")
  val answer: Float = sum / numIterations.toFloat
  println(s"Pi counted is ${4 * answer}")

* */