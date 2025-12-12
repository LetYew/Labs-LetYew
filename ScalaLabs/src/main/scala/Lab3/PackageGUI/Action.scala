package Lab3.PackageGUI

import Lab3.MenuClasses.Drink
import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.*
import scala.language.postfixOps



//Прототип класса действия для кнопки
sealed trait Action {
  def executeAction(): Unit
}

//Добавить напиток в ObservableBuffer[] представляющий заказ
case class AddToOrderAction(
                            menuTable: TableView[(Drink, Double)],
                            orderedDrinks: ObservableBuffer[(Drink, Double)]
                          ) extends Action {
  override def executeAction(): Unit = {
    val selectedDrink: (Drink, Double) = menuTable.selectionModel().selectedItem.value
    selectedDrink match {
      case null => println("WARNING: No drink selected to ADD")
      case _ => orderedDrinks += selectedDrink
    }
  }
}


//Убрать напиток из таблицы
case class RemoveFromOrderAction(
                                  orderTable: TableView[(Drink, Double)],
                                  orderedDrinks: ObservableBuffer[(Drink, Double)]
                                ) extends Action {
  override def executeAction(): Unit = {
    val selectedDrink: (Drink, Double) = orderTable.selectionModel().selectedItem.value
    selectedDrink match {
      case null => println("WARNING: No drink selected to REMOVE")
      case _ => orderedDrinks -= selectedDrink
    }
  }
}

//Очищает таблицу заказов
case class ClearOrderAction(
                             orderedDrinks: ObservableBuffer[(Drink, Double)]
                           ) extends Action {
  override def executeAction(): Unit = {
    orderedDrinks.clear()
  }
}

//Вызывает диалоговое окошечко
case class CreateNewAddition(
                            additionCreateDialog: Dialog[Unit]
                            ) extends Action {

  override def executeAction(): Unit = {
    additionCreateDialog.showAndWait()
  }
}

//Вызывает диалог применения добавки к напитку
case class ApplyAddition(
                        applyAdditionDialog: Dialog[Unit],
                        orderTable: TableView[(Drink, Double)]
                        ) extends Action {
  override def executeAction(): Unit = {
    val selected = orderTable.selectionModel().selectedItem.value
    if(selected != null) {
      applyAdditionDialog.showAndWait()
    }
  }
}

case class getCheck(
                     orderedDrinks: ObservableBuffer[(Drink, Double)]
                   ) extends Action {
  override def executeAction(): Unit = {
    var totalCost: Double = 0d
    if (orderedDrinks.isEmpty) {
      println("==========ORDER IS EMPTY==========")
      return
    }
    orderedDrinks.foreach { case (key, value) =>
      println("==========CHECK FOR ORDER==========")
      var drinkCost: Double = 0d
      println(s"Drink: ${key.drinkName}, Value: $value")

      drinkCost += value

      //тут я не придумал как печатать в одной строке, типа println(s"Additions: ${...}"), так что вынес вовне
      println("Additions: ")
      //match case чтобы если допов(Additions) нет вывести явно в текст
      key.additions match {
        case Nil =>
          println("\tNo Additions")
        case _ =>
          key.additions.foreach(item =>
            println('\t' + item.name + ": " + item.additionalPrice)
            drinkCost += item.additionalPrice
          )
      }
      totalCost += drinkCost
      println(s"Total Drink Cost: ${drinkCost}")
    }
    println("==========")
    println(s"\tTotal Order Cost is: ${totalCost}")
  }
}