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