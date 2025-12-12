package Lab3.PackageGUI

import Lab3.MenuClasses.{Addition, Drink, Producer, myMenu}
import Lab3.PackageGUI.*
import Lab3.PackageGUI.ButtonManager._
import javafx.scene.paint.Color.*
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.*
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text

import scala.language.postfixOps

/**
 * <h1>Что это такое?</h1>
 * <p>
 *   В общем и целом я решил выпендриться и сделать GUI.
 * Я не совсем уверен зачем и почему, но он будет сделан,
 * правда UI-дизайнер из меня как... в общем, плохо все с дизайном.
 * Зато я знаю как кнопочку добавить
 * </p>
 * <p>
 *   Это GUI. Таких как он много, но этот - мой!
 *   Я написал это чтобы хоть как-то спасти это недоразумение,
 *   называемое лабой.
 * </p>
 * */

case class GUI(createdMenu: myMenu) extends JFXApp3 {

  val additions = ObservableBuffer[Addition]()

  val producers = ObservableBuffer[Producer]()

  //Меню
  private val displayedMenu = createdMenu

  //Списко выбранных напитков
  private val selectedDrinks = ObservableBuffer[(Drink, Double)]()

  //Новый контейнер под кнопочки
  private val buttonContainer = ButtonContainer.buttonContainer

  //Переменная стоимости заказа
  private val totalCost = {
    updateTotalCostText()
  }

  //Обновляет текст на екстовом поле
  private def updateTotalCostText(): String = {
    "Итого: " +
    selectedDrinks.map((drink, price) =>
      val addedPrice = drink.additions.map(_.additionalPrice).sum
      addedPrice + price
    ).sum.toString
  }

  override def start(): Unit = {

    //Приводит меню в нужный формат
    def formatMenu: ObservableBuffer[(Drink, Double)] = {
      val createdBuffer = ObservableBuffer[(Drink, Double)]()
      displayedMenu._1.foreach { case (drink, price) =>
        createdBuffer += (drink -> price)
      }
      createdBuffer
    }

    //Таблица меню
    val menuTable: TableView[(Drink, Double)] = new TableView[(Drink, Double)](formatMenu) {
      columns ++= List(

        //мудрый дядька на StackOverflow говорит, если нужен ObservableValue -
        //делай, оберни. Я, собсна, обернул
        //Без StringProperty(...) жалуется что значение не то передаю
        //Мне кажется это костыль, но может так и надо
        //https://stackoverflow.com/questions/35534723/convert-a-string-to-an-observablevaluestring
        new TableColumn[(Drink, Double), String] {
          text = "Название"
          cellValueFactory = { data =>
            StringProperty(data.value._1.drinkName)
          }
          prefWidth = 150
        },
        //Основа напитка
        new TableColumn[(Drink, Double), String] {
          text = "Основа"
          cellValueFactory = { data =>
            StringProperty(data.value._1.drinkType.toString)
          }
          prefWidth = 150
        },
        //Цена базы напитка
        new TableColumn[(Drink, Double), String] {
          text = "Цена"
          cellValueFactory = { data =>
            StringProperty(data.value._2.toString)
          }
        },
        //Допы
        new TableColumn[(Drink, Double), String] {
          text = "Дополнения"
          cellValueFactory = { data =>
            data.value._1.additions match {
              case Nil => StringProperty("No Additions")
              case _ =>  StringProperty(data.value._1.additions.map(_.name).mkString(", "))
            }
          }
        },

        new TableColumn[(Drink, Double), String] {
          text = "Цена дополнений"
          cellValueFactory = { data =>
            data.value._1.additions match {
              case Nil => StringProperty("None")
              case _ =>  StringProperty(data.value._1.additions.map(_.additionalPrice).mkString(", "))
            }
          }
        }
      )
    }

    //Таблица заказа
    val orderTable = new TableView[(Drink, Double)](selectedDrinks) {
      columns ++= List(
        new TableColumn[(Drink, Double), String] {
          text = "Название"
          cellValueFactory = { data =>
            StringProperty(data.value._1.drinkName) }
          prefWidth = 150
        },
        new TableColumn[(Drink, Double), String] {
          text = "Тип"
          cellValueFactory = { data =>
            StringProperty(data.value._1.drinkType.toString) }
          prefWidth = 100
        },
        new TableColumn[(Drink, Double), String] {
          text = "Общая цена"
          cellValueFactory = { data =>
            val basePrice = data.value._2
            val additionsPrice = data.value._1.additions.map(_.additionalPrice).sum
            StringProperty((basePrice + additionsPrice).toString)
          }
          prefWidth = 100
        },
        new TableColumn[(Drink, Double), String] {
          text = "Дополнения"
          cellValueFactory = { data =>
            data.value._1.additions match {
              case Nil => StringProperty("No Additions")
              case _ =>  StringProperty(data.value._1.additions.map(_.name).mkString(", "))
            }
          }
        },
      )
    }


    //Создаем кнопочки
    val addToOrderButtonManager = createAddToOrderButton(menuTable, selectedDrinks)
    val removeFromOrderButtonManager = createRemoveFromOrderButton(orderTable, selectedDrinks)
    val clearOrderButtonManager = createClearOrderButton(selectedDrinks)
    val createNewAdditionButtonManager = createNewAdditionCreationButton(additions)
    val applyAdditionButtonManager = createApplyAdditionButton(additions, orderTable, selectedDrinks)
    val getCheckButtonManager = createGetCheckButton(selectedDrinks)

    //Кидаем в контейнер
    //Контейнер для удобства. Здесь не используется, но вообще с ним удобнее
    buttonContainer.addButton("Add To Order", addToOrderButtonManager)
      .addButton("RemoveFromOrder", removeFromOrderButtonManager)
      .addButton("ClearOrder", clearOrderButtonManager)
      .addButton("CreateNewAddition", createNewAdditionButtonManager)
      .addButton("ApplyAddition", applyAdditionButtonManager)
      .addButton("GetCheck", getCheckButtonManager)


    //Текстовое поле показывает полную стоимость заказа
    //Динамически обновляется(onChange)
    val totalCostText = new Text(totalCost){
      fill = INDIANRED
    }
    selectedDrinks.onChange(
      totalCostText.text = updateTotalCostText()
    )

    //ToolBar с пользовательскими кнопками
    val toolBarButtons = new ToolBar() {
      content = Seq(
        addToOrderButtonManager.button,
        removeFromOrderButtonManager.button,
        clearOrderButtonManager.button,
        getCheckButtonManager.button,
        applyAdditionButtonManager.button
      )
    }

    //ToolBar с админскими кнопками(добавить опции там и т.д.)
    val toolBarAdminOptions = new ToolBar() {
      content = Seq(
        createNewAdditionButtonManager.button,
      )
    }


    //Layout для виджета
    val mainLayout = new VBox(15) {
      padding = Insets(20)
      children = Seq[scalafx.scene.Node](
        new Label("Лабораторная работа №3"),
        //testButton,
        toolBarAdminOptions,
        new Label("Меню:"),
        menuTable,
        new Label("Заказ:"),
        orderTable,
        totalCostText,
        toolBarButtons
      )
    }

    stage = new JFXApp3.PrimaryStage {
      title.value = "Lab3 GUI"
      //размеры окна
      width = 650
      height = 600
      resizable = false

      //Содержание виджета
      scene = new Scene {
        root = mainLayout//Просто лэйаут
      }
    }
  }
}