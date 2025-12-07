package Playground

import Lab3.MenuClasses.Addition
import javafx.scene.paint.Color.*
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.*
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text

import scala.language.postfixOps
import scala.math.Ordered.orderingToOrdered


def createAdditionInDialog(name: String, price: Double): Addition = {
  {
    val alert = new Alert(AlertType.Error, "Wrong Parameters")

    name match {
      case "" => alert.showAndWait()
      case _ => println(s"Имя корректно: ${name}")
    }

    Option(price) match {
      case None => alert.showAndWait()
      case Some(value) => println(s"Цена корректан: ${price}")
    }
    /*return*/ Addition(name, price)

  }
}


/*
//Много кофейных сиропов не бывает...
val additionCreateDialog: Dialog[Addition] = new Dialog[Addition] {
  title = "Создание новой добавки"

  //Название
  val additionNameInput = new TextField() {
    promptText = "Сахар, Соль, Стаут"
  }
  val newAdditionName: String = additionNameInput.text.value
  //Цена
  val additionPriceInput = new TextField(){
    promptText = "Значение >0"
  }
  val newAdditionPrice: Option[Double] = additionPriceInput.text.value.toDoubleOption


  dialogPane().content = new VBox(15){
    children = Seq(
      new Label("Введите название добавки"),
      additionNameInput,
      new Label("Введите стоимость добавки"),
      additionPriceInput,
      new Button("Подтвердить") {
        onAction = _ => {
          val alert = new Alert(AlertType.Error, "Wrong Parameters")


          newAdditionName match {
            case "" => alert.showAndWait()
            case _ => println(s"Добавка \"${additionNameInput.text.value}\" добавлена")
          }

          newAdditionPrice match {
            case Some(value) => println(s"Добавка \"${additionPriceInput.text.value}\" добавлена")
            case None => alert.showAndWait()
          }
          //TODO Какнибудь убить диалог при нажатии кнопки
          additionCreateDialog.close()
        }

      },
      new Button("Отмена") {
        onAction = _ => additionCreateDialog.close()//я почти уверен что это небезопасная хтонь
      }
    )
  }
  dialogPane().buttonTypes = Seq(ButtonType.Apply, ButtonType.Close)
}
*/
//Много кофейных сиропов не бывает...
/*val additionCreateDialog: Dialog[Addition] = new Dialog[Addition] {
  title = "Создание новой добавки"

  val additionNameInput = new TextField()
  val additionPriceInput = new TextField()

  val newAdditionName: String = additionNameInput.text.value
  val newAdditionPrice: Option[Double] = additionPriceInput.text.value.toDoubleOption

  dialogPane().content = new VBox(15){
    children = Seq(
      new Label("Введите название добавки"),
      additionNameInput,
      new Label("Введите стоимость добавки"),
      additionPriceInput,
      new Button("Подтвердить") {
        onAction = _ =>
          createAdditionInDialog(additionNameInput.text.value, additionPriceInput.text.value.toDouble)
      },
      new Button("Закрыть") {
       onAction = _ => additionCreateDialog.close()
      }


    )
  }
}*/
val additionCreateDialog: Dialog[Addition] = new Dialog[Addition] {
  title = "Создание новой добавки"

  val additionNameInput = new TextField()
  val additionPriceInput = new TextField()

  var additionName = additionNameInput.text.value
  var additionPrice = additionPriceInput.text.value.toDoubleOption

  dialogPane().content = new VBox(15){
    children = Seq(
      new Label("Введите название добавки"),
      additionNameInput,
      new Label("Введите стоимость добавки"),
      additionPriceInput,

      new Button("Создать добавку") {
        onAction = _ =>
          additionName match {
            case "" => println("Всё НЕ ок")
            case _ => println("Всё ок")
          }
          
          additionPrice match {
            case Some(value) => println("Всё ок")
            case None => println("Всё НЕ ок")
          }
      }
    )
  }

  /*return additionCreateDialog.result = Addition(newAdditionName, newAdditionPrice.get)*/
}

case class testGUI() extends JFXApp3 {
  override def start(): Unit = {

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene {
        root = new VBox(15) {
          children = Seq(
          new Button("Click Me"){
            onAction = _ =>
              val answer = additionCreateDialog.showAndWait()
              println(answer)
          }
          )
        }
      }
    }
  }
}