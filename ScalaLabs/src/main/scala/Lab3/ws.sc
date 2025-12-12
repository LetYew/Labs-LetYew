sealed trait Drink
sealed trait Tea extends Drink
type Color
case class SimpleTea(color:Color)
case class Addition(name:String, price:Double)

case class FullDrink(drink: Drink, additions:List[Addition])

def readAdditions():List[Addition] = {
  println("input additions number")
  // читаем колчиество
  // читаем n значений
  ???
}
def readDrink():Drink = ???
def readFullDrink():FullDrink = {
  FullDrink(readDrink(), readAdditions())
}

def addAddition(fullDrink: FullDrink, addition: Addition):FullDrink =
  FullDrink(fullDrink.drink, fullDrink.additions.appended(addition))
