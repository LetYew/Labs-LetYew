//noinspection RedundantBlock
package Lab3.PackageGUI

import Lab3.MenuClasses.{Addition, Drink}
import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.*
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.VBox
import scala.language.postfixOps

sealed trait DialogBuilder {
  def buildAndReturn(): Dialog[Unit]
}

//Возвращает собранный Диалог
case class BuildCreateAdditionDialog(
                                      additions: ObservableBuffer[Addition]
                                    ) extends DialogBuilder {

  val additionNameInput: TextField = new TextField() {
    text = ""
    promptText = "Сахар, Соль, Стаут"
  }
  val additionPriceInput: TextField = new TextField() {
    text = ""
    promptText = "Значение >0"
  }

  override def buildAndReturn(): Dialog[Unit] = {
    //Диалоговое окно
    val additionCreateDialog: Dialog[Unit] = new Dialog[Unit]

    //Выставляем контент для диалогового окна
    //Тут я хочу оставить дефолтные ButtonTypes
    additionCreateDialog.dialogPane().content = new VBox(15) {
      children = Seq(
        new Label("Введите название добавки"),
        additionNameInput,
        new Label("Введите стоимость добавки"),
        additionPriceInput,
      )
    }
    
    //Выставляем ButtonTypes
    //Просто две кнопки Apply/Cancel
    additionCreateDialog.dialogPane().buttonTypes = Seq(
      ButtonType.Apply,
      ButtonType.Close
    )

    //Обработчик результата
    //Решает что случится когда пользователь нажмет на каждую кнопку
    additionCreateDialog.resultConverter = {
      case ButtonType.Apply => {
        if(additionNameInput.text.value != "" && additionPriceInput.text.value.toDoubleOption.isDefined){
          additions.add(Addition(additionNameInput.text.value, additionPriceInput.text.value.toDouble))
        }
        else {
          Alert(AlertType.Error, "Wrong Parameters Stated During addition creation Process").showAndWait()
          additionCreateDialog.showAndWait()
        }
      }
      case ButtonType.Close => {
        //Тут ничего, так как ButtonType.Close сам и закрывает всё
      }
    }
    
    /*Волею Господа пишу сюда return*/additionCreateDialog
  }

  //Сброс текста
  val resetInputText: () => Unit = 
    () => {
    additionNameInput.text = ""
    additionPriceInput.text = ""
  }
}




case class BuildApplyAdditionDialog(
                                      additions: ObservableBuffer[Addition],
                                      orderTable: TableView[(Drink, Double)],
                                      orderedDrinks: ObservableBuffer[(Drink, Double)]
                                    ) extends DialogBuilder {

  private val selectAddition: ComboBox[Addition] = new ComboBox[Addition](additions) {
    prefWidth = 150
    //Жуть какая, он тут требует ListCell, страшновое
    cellFactory = { _ =>
      new ListCell[Addition] {
        item.onChange { (_, _, item) =>
          text = if (item != null) s"${item.name}: ${item.additionalPrice}" else null
        }
      }
    }
  }

  override def buildAndReturn(): Dialog[Unit] = {

    val applyAdditionDialog: Dialog[Unit] = new Dialog[Unit] {
      title = "Доступные добавки"
    }

    applyAdditionDialog.dialogPane().content = new VBox(15) {
      children = Seq(
        new Label("Селектор добавок"),
        selectAddition
      )
    }
    applyAdditionDialog.dialogPane().buttonTypes = Seq(ButtonType.Cancel, ButtonType.Apply)

    //Обработчик результата
    applyAdditionDialog.resultConverter = {
      case ButtonType.Apply => {
        //TODO ASK Иммутабельности никакой, так вообще можно???
        val selectedAddition: Addition = selectAddition.value.value
        if(selectedAddition != null){
          val selected = orderTable.selectionModel().selectedItem.value
          val newDrink: Drink = selected._1.copy(additions = selected._1.additions :+ selectedAddition)
          orderedDrinks.update(orderedDrinks.indexOf(selected), (newDrink, selected._2))
        }
      }
      case ButtonType.Cancel => {
        //Тут ничего, close сам должен все закрыть
      }
    }


    /*return please*/applyAdditionDialog
  }
}