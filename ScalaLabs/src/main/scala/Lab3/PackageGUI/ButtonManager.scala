package Lab3.PackageGUI

import Lab3.MenuClasses.{Addition, Drink}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.*

import scala.concurrent.{Future, Promise}
import scala.language.postfixOps

class ButtonManager(
                     val button: Button,
                     private val action: Action,
                     private val promises: LazyList[Promise[Action]] = LazyList.continually(Promise[Action]())
                   ) {

  //Текущий промис для следующего действия
  private var currentPromises = promises


  private def initializeButton(): Unit = {
    button.onAction = _ => {
      // Выполняем текущее действие
      action.executeAction()

      //Если проми имеется, завершаем
      //Так как LazyList continually, по сути просто бесконечный ЛенивыйЛист действий кнопки
      if (currentPromises.nonEmpty) {
        currentPromises.head.success(action)//Выполняем
        currentPromises = currentPromises.tail//Переопределяем
        //фиксируем прибыль
      }
    }
  }

  //Инициализация кнопки
  initializeButton()

  // Получить Future для следующего нажатия кнопки
  def nextActionFuture(): Future[Action] = {
    if (currentPromises.nonEmpty) {
      currentPromises.head.future
    } else {
      Future.failed(new NoSuchElementException("No more promises available"))
    }
  }

  // Получить все Future для всех будущих нажатий
  def getAllActionFutures: LazyList[Future[Action]] = {
    promises.map(_.future)
  }

  // Создать клон с новым действием
  def copyWithNewAction(newAction: Action): ButtonManager = {
    new ButtonManager(button, newAction, promises)
  }
}




object ButtonManager {

  def createAddToOrderButton(
                              menuTable: TableView[(Drink, Double)],
                              orderedDrinks: ObservableBuffer[(Drink,Double)]
                            ): ButtonManager = {
    val newAddToOrderButton = new Button("Добавить в заказ")
    val addToOrderAction = AddToOrderAction(menuTable, orderedDrinks)

    //Возвращаем новый объект менеджера кнопки
    new ButtonManager(newAddToOrderButton, addToOrderAction)
  }

  //def create
  def createRemoveFromOrderButton(
                                   orderedTable: TableView[(Drink, Double)],
                                   orderedDrinks: ObservableBuffer[(Drink,Double)]
                                 ): ButtonManager = {
    val newRemoveFromOrderButton = new Button("Удалить из заказа")
    val removeFromOrderAction = RemoveFromOrderAction(orderedTable, orderedDrinks)

    //Возвращаем новый объект менеджера кнопки
    new ButtonManager(newRemoveFromOrderButton, removeFromOrderAction)
  }

  def createClearOrderButton(
                              orderedDrinks: ObservableBuffer[(Drink,Double)]
                            ): ButtonManager = {
    val clearOrderButton = new Button("Очистить заказ")
    val clearOrderAction = ClearOrderAction(orderedDrinks)

    new ButtonManager(clearOrderButton, clearOrderAction)
  }

  def createNewAdditionCreationButton(
                                       additions: ObservableBuffer[Addition]
                                ): ButtonManager = {
    //Диалоговое окно
    val newCreateAdditionDialog: Dialog[Unit] = BuildCreateAdditionDialog(additions).buildAndReturn()
    
    val createNewAdditionButton = new Button("Новая Добавка")
    val createNewAdditionAction = CreateNewAddition(newCreateAdditionDialog)

    new ButtonManager(createNewAdditionButton, createNewAdditionAction)
  }
  
  def createApplyAdditionButton(
                                 additions: ObservableBuffer[Addition],
                                 orderedTable: TableView[(Drink, Double)],
                                 orderedDrinks: ObservableBuffer[(Drink, Double)]
                               ): ButtonManager = {
    val applyAdditionDialog = BuildApplyAdditionDialog(additions, orderedTable, orderedDrinks).buildAndReturn()
    
    val applyAdditionButton = new Button("Применить Добавку")
    val applyAdditionAction = ApplyAddition(applyAdditionDialog, orderedTable)
    
    new ButtonManager(applyAdditionButton, applyAdditionAction)
  }
  
  def createGetCheckButton(
                          orderedDrinks: ObservableBuffer[(Drink, Double)]
                          ): ButtonManager = {
    val getCheckButton = new Button("Получить чек")
    
    val getCheckAction = getCheck(orderedDrinks)
    
    new ButtonManager(getCheckButton, getCheckAction)
  }
}