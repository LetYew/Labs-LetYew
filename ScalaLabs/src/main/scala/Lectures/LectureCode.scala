package Lectures

//11.11
/**
 * 1. Написать реализацию листа<br>
 * 2. Описать реализую, отражающую функторность листа
 */

@main
def main(): Unit = {
  //Функтор - объект как функция
  trait MyFunctor[F[_]]:
    def map[A, B](f: A => B): F[A] => F[B]


  sealed trait MyList[+T]:
    def head: T

    def tail: List[T]

  case object MyNil extends MyList[Nothing]:
    override def head: Nothing = throw new NoSuchElementException("No Head???")

    override def tail: List[Nothing] = throw new NoSuchElementException("NoTail???")

  case class MyNode[T](h: T, t: MyList[T]) extends MyList[T]:
    override def head: T = head

    override def tail: List[T] = tail

  object MyListFunctorImpl extends MyFunctor[MyList]:
    def map[A, B](f: A => B): MyList[A] => MyList[B] = {
      case MyNil => MyNil
      case MyNode(h, t) => MyNode(f(h), map(f)(t))
    }


  //Option
  sealed trait MyOpt[+T]
  case class MySome[T](x: T) extends MyOpt[T]
  case object MyNone extends MyOpt[Nothing]

  object MyOptFunctorImpl extends MyFunctor[MyOpt]:
    override def map[A, B](f: A => B): MyOpt[A] => MyOpt[B] = {
      case MyNone => MyNone
      case MySome(x) => MySome(f(x))
    }

  //Если к пустому элементу - это не положит весь код одним exception-ом
  def SafeAlarm[T](list: MyList[T]): MyOpt[MyList[T]] =
    list match {
      case MyNil => MyNone
      case MyNode(h, t) => MySome(t)
    }

  object MyOptListFunctorImpl extends MyFunctor[[X] =>> MyOpt[MyList[X]]]:
    //myLitOpt => MyOptFunctorImpl.map(MyListFunctorImpl.map(f))(myListOpt) - страшно
    override def map[A, B](f: A => B): MyOpt[MyList[A]] => MyOpt[MyList[B]] =
      {
        case MyNone => MyNone
        case MySome(x) => MySome(MyListFunctorImpl.map(f)(x))
      }



  def hello(): Unit = println("Hiii Hello Hello Hiii Hiii^-^")
}