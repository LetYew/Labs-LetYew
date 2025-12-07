package Lab2

/**<h1>Пометочки:</h1>
 * <h2><cite>"Это мой object Circle{...}. Таких, как он, много, но этот - мой"</cite></h2>
 * <p>
 *  1)Вообще можно бы и class, просто все равно круг один только нужен, а тут так удачно есть удобная конструкция<br>
 *  2) Обернул в <i>Option[_]</i> потому что случайно нашел такую штуку, показалось полезно для обработки ошибок<br>
 * </p>
 * В общем, единственная причина почему это так замудрёно - я открыл для себя
 * <a href = "https://docs.scala-lang.org/ru/tour/tour-of-scala.html">docs.scala-lang.org</a>
 */

object Circle {
  private var radius: Option[Float] = None

  //(***|===================-
  def setRadius(newRadius: Float): Unit = {
    radius = Some(newRadius)
  }

  def getRadius: Float = radius match{
    case Some(radius) => radius
    case None => throw new NoSuchElementException("ERROR: No radius currently stated for Circle")
    //Да, я хочу чтобы он ронял код Exception-ом, нравится мне так -_-
  }
}
