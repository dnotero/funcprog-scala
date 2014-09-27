package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = ???

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def valueOf(c: Char): Int = {
      if(c == '(')1
      else if(c == ')') -1
      else 0
    }

    def balance(acc: Int, chars: List[Char]): Boolean = {
      if(chars.isEmpty) true
      else !(acc + valueOf(chars.head) < 0) && balance(acc + valueOf(chars.head), chars.tail)
    }

    balance(0, chars)
  }


  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
