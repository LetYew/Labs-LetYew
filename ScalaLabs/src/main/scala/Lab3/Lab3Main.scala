package Lab3

import Lab3.MenuClasses.{BlackTea, Drink, GreenTea, Producer, myMenu}
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
 * <p>В общем, что сделано в итоге:<br>
 * Есть система заказа с "полноценным" GUI реализованным силами ScalaFX.
 * Полученный виджет позволяет составить заказ из нескольких позиций, добавить к каждой позиции дополнение,
 * создать новое дополнения для напитка и вернуть форматированный чек по заказу<br>
 * <b>LazyList[]</b> был применен для организаци GUI, в частности для обработки кннопок.
 * В итоговом проекте нет необходимости(на наш взгляд) применять <b>LazyList</b> где либо еще.
 * <ul>
 * <li>Для организзации Меню нет смысла, т.к. меню - относительно небольшой объект, да и Map просто логичнее</li>
 * <li>Для хранения заказов тоже нет необходимости, тем более что в рамках ScalaFX
 * для динамически обновляемых таблиц нужны скорее Observable... данные</li>
 *
 *
 * </ul>
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
  
  println(CaffeMenu.calculateTotal())
    
  println("DRAWING GUI")
  val test: Unit = GUI(CaffeMenu).main(Array())
}
