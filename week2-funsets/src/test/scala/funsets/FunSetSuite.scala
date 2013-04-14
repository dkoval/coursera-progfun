package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 * - run the "test" command in the SBT console
 * - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   * - test
   * - ignore
   * - pending
   */

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val integers = (x: Int) => true
    val evens = (x: Int) => x % 2 == 0
    val unevens = (x: Int) => x % 2 == 1
    val multsOf4 = (x: Int) => x % 4 == 0
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("singletonSet(1) doesn't contain 2") {
    new TestSets {
      assert(!contains(s1, 2), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("empty intersection contains no elements") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Intersection 1")
      assert(!contains(s, 2), "Intersection 2")
    }
  }

  test("difference contains no elements") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1), "Difference 1")
      assert(!contains(s, 2), "Difference 2")
      assert(!contains(s, 3), "Difference 3")
    }
  }

  test("filter") {
    new TestSets {
      val s = filter(evens, x => x > 0)
      assert(contains(s, 2), "Positive Evens 2")
      assert(!contains(s, -2), "Positive Evens -2")
      assert(!contains(s, 1), "Positive Evens 1")
      assert(!contains(s, 3), "Positive Evens 3")
    }
  }

  test("forall") {
    new TestSets {
      assert(forall(multsOf4, evens), "All multiples of 4 are even")
      assert(!forall(evens, multsOf4), "All evens aren't divisible by 4")
    }
  }

  test("exists") {
    new TestSets {
      assert(exists(evens, multsOf4), "Multipliers of 4 exist in the evens")
      assert(!exists(multsOf4, x => x == 3), "3 doesn't exist in the multiples of 4")
    }
  }

  test("map integers times 2") {
    new TestSets {
      val s = map(integers, x => 2 * x)
      assert(exists(s, multsOf4), "Multipliers of 4 exist in the evens")
      assert(!exists(s, unevens), "Uneven number doesn't exist in the evens")
    }
  }
}
