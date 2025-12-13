package Lab3

import Lab3.MenuClasses.*
import Lab3.PackageGUI.GUI

import scala.language.postfixOps

/**
 * <h1>Вариант 1</h1>
 * <p id = "drinkNames">
 * Посетитель может заказать себе напитки:
   * <dl>
   *  <dt><b>Чай</b></dt>
   *    <dd>- черный</dd>
   *    <dd>- зеленый</dd>
   *  <dt><b>Кофе</b></dt>
   *    <dd>- черный</dd>
   *    <dd>- с молоком</dd>
 *
   * </dl>
 * </p>
 * <hr>
 * <p id = "additionalDetails">
 *   <h2>Свойства напитков</h2>
 *  <ol start = 1>
 *   <li>Напитки могут быть от разных производителей.</li>
 *   <li>Дополнительно в чай или кофе может быть положен сахар.</li>
 *   <li>Каждый компонент обладает своей стоимостью.</li>
 *  </ol>
 * Реализовать классовую модель и приложение, позволяющее
 * <strong>формировать меню</strong> и <strong>пересчитывать его стоимость</strong>.
 * </p>
 * <hr>
 *
 *
 * <br>
 * <img src = "LiterallyTargetAudience.jpg">
 */


@main
def main(): Unit = {

  //Пресеты
  val greenfield = Producer("Greenfield")
  
  val PlainBlackTea = Drink("Black Tea With Sugar", BlackTea, greenfield, Nil, 10)
  val PlainGreenTea = Drink("Plain Green Tea", GreenTea, greenfield, Nil, 7)
  
  val CaffeMenu = myMenu.myMenu.addDrink(PlainBlackTea)
    .addDrink(PlainGreenTea)

    
  println("DRAWING GUI")
  val test: Unit = GUI(CaffeMenu).main(Array())
}
