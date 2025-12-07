package Lab3.MenuClasses

import Lab3.MenuClasses.myMenu

import scala.language.postfixOps



case class myMenu(private val menuPositions: Map[Drink, Double]) {
  //Добавляет напиток
  def addDrink(drink: Drink): myMenu = {
    copy(menuPositions = menuPositions + (drink -> (drink.basePrice)))
  }
  
  //Удаляет напиток -_-
  def removeDrink(drink: Drink): myMenu =
    copy(menuPositions = menuPositions - drink)

  //Выводит в консольку форматированную строку с всеми напитками
  def printDrinks(): Unit = {
    var totalCost: Double = 0d
    menuPositions.foreach { case (key, value) =>
      println("==========")
      var drinkCost: Double = 0d
      println(s"Drink: ${key.drinkName}, Value: $value")

      println(s"Base is ${key.drinkType}")

      drinkCost += value

      //тут я не придумал как печатать в одной строке, типа println(s"Additions: ${...}"), так что вынес вовне
      println("Additions: ")
      //match case чтобы если допов(Additions) нет вывести явно в текст
      key.additions match {
      case Nil =>
        println("\tNo Additions")
      case _  =>
        key.additions.foreach(item =>
          println('\t' + item.name + ": " + item.additionalPrice)
          drinkCost += item.additionalPrice
        )
      }
      totalCost += drinkCost
      println(s"Total Drink Cost: ${drinkCost}")
    }
    println("==========")
    println(s"\tTotal myMenu Cost is: ${totalCost}")
  }


  def listByType(drinkType: DrinkType): Map[Drink, Double] =
    menuPositions.filter { case (drink, _) => drink.drinkType == drinkType }
  
  
  def calculateTotal(): Double = {
    var totalCost: Double = 0d
    menuPositions.foreach(item =>
      totalCost += item._2
      totalCost += item._1.additions.map(_._2).sum
    )
    totalCost
  }

}


object myMenu {
  val myMenu: myMenu = new myMenu(Map.empty)
}