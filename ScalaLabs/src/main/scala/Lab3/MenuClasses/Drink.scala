package Lab3.MenuClasses

/**
 * <h1>Это попытка в Ф(ОО)П</h1>
 * <p>
 *   В общем и целом здесь местный скалистый enum и
 *   <strong>класс образец</strong>. В другом языке и с ООП сделал бы что-нибудь
 *   подобное скорее всего, как еще добиться иммутабельности я хз
 * </p>
 * <p>
 *   Важности по этой работе в целом
 * </p>
*/

sealed trait DrinkType

//Чаи
case object BlackTea extends DrinkType
case object GreenTea extends DrinkType
case object OtherTea extends DrinkType
//Кофий
case object BlackCoffee extends DrinkType
case object MilkCoffee extends DrinkType
case object OtherCoffee extends DrinkType

//Производитель
case class Producer(name: String)

//Добавка к напитку
case class Addition(name: String,
                    additionalPrice: Double)

//Сам напиток
case class Drink(
                  drinkName: String,
                  drinkType: DrinkType,
                  producer: Producer,
                  additions: List[Addition] = Nil,
                  basePrice: Double
                )