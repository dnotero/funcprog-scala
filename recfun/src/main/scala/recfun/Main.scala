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
    
    def balance(acc: Int, chars: List[Char]): Boolean = {
      def valueOf(s: Char) = 
        if('('.equals(s)) {
          1
        } else if(')'.equals(s)) {
          -1
        } else {
          0
        }
      }

      if(chars.isEmpty) {
        acc == 0
      } else {
        val c: Char = chars.head
        if(acc + valueOf(c) < 0) {
          false
        } else {
          balance(acc + valueOf(c), chars.tail)
        }
      }
    }

    balance(0, chars)
  }


  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
