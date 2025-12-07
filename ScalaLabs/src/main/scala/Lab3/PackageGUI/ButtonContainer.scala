package Lab3.PackageGUI

/**
 * Вообще можно обойтись и просто Map-ой в самом GUI, но я уж вынес раз начал в отдеьлную пару case class <-> object
 * <br>DTO-like штуковина
 */
case class ButtonContainer(private val buttonManagers: Map[ButtonManager, String]) {
  
  def addButton(managerName: String, manager: ButtonManager): ButtonContainer = {
    copy(buttonManagers = buttonManagers + (manager -> managerName))
  }
  
  def removeButton(managerName: String, manager: ButtonManager): ButtonContainer = {
    copy(buttonManagers = buttonManagers - manager) 
  }
}

object ButtonContainer {
  val buttonContainer = new ButtonContainer(Map.empty)
}