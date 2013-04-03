package recfun

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
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def countMismatches(chars: List[Char], numOpenParentheses: Int) : Int = {
      if (chars.isEmpty || numOpenParentheses < 0) numOpenParentheses
      else if (chars.head == '(') countMismatches(chars.tail, numOpenParentheses + 1)
      else if (chars.head == ')') countMismatches(chars.tail, numOpenParentheses - 1)
      else countMismatches(chars.tail, numOpenParentheses)
    }
    countMismatches(chars, 0) == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    // We have no money, exactly one way to solve the problem - by choosing no coin change,
    // or, more precisely, to choose coin change of 0
    if (money == 0) 1
    // No solution - negative sum of money
    else if (money < 0) 0
    // no solution - we have money, but no change available
    else if (coins.isEmpty) 0
    // The set of solutions for this problem can be partitioned into two sets:
    // - those sets that contain at least 1 coins.head
    // - there are those sets that do not contain any coins.head
    // This partitioning will essentially break the initial problem into two subproblems:
    // - a solution does in fact contain coins.head, then we are using at least one counts.head,
    // thus we are now solving the subproblem of (money - coins.head)
    // - a solution does not contain coins.head, then we can solve the subproblem of money with coins.tail
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
