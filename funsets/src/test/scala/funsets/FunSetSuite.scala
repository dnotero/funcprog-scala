package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
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
   *   val s1 = singletonSet(1)
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

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersection contains only elements on both sets") {
    new TestSets {
      val u = union(s1, s2)
      val s = intersect(u, s1)
      assert(contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersection of disjoint sets returns empty set") {
    new TestSets {
      val u = union(s1, s2)
      val s = intersect(u, s3)
      assert(!contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("diff contains the elements of one set that are not in the second set") {
    new TestSets {
      val u = union(s1, s2)
      val s = diff(u, s1)

      assert(!contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("diff of disjoint set returns an empty set") {
    new TestSets {
      val u = union(s1, s2)
      val s = diff(s1, u)

      assert(!contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("filter returns elements for which a predicate holds") {
    new TestSets {
      val u = union(union(s1, s2), s3)
      val s = filter(u, x => x % 2 == 0)

      assert(!contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("filter empty set when predicate doesn't hold") {
    new TestSets {
      val u = union(union(s1, s2), s3)
      val s = filter(u, x => x < 0)

      assert(!contains(s, 1), "Union 1")
      assert(!contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("forall") {
    new TestSets {
      assert(forall(union(union(s1, s2), s3), x => x > 0), "all elemets hold the predicate")
    }
  }

  test("forall doesn't hold for everyone") {
    new TestSets {
      assert(!forall(union(union(s1, s2), s3), x => x != 1), "not all elements hold the predicate")
    }
  }

  test("exists") {
    new TestSets {
      assert(exists(union(union(s1, s2), s3), x => x > 0), "all elemets hold the predicate")
      assert(exists(union(union(s1, s2), s3), x => x != 1), "at least one element holds the predicate")
    }
  }

  test("exists doesn't hold for at least one") {
    new TestSets {
      assert(!exists(union(union(s1, s2), s3), x => x < 0), "none of the elements hold the predicate")
    }
  }

  test("map returns a set which members are the double of other") {
    new TestSets {
      val result = map(singletonSet(4), x => 2 * x)

      assert(contains(result,8), "none of the elements hold the predicate")
      assert(!contains(result,4), "none of the elements hold the predicate")
      assert(!contains(result,3), "none of the elements hold the predicate")
    }
  }

  test("coursera test") {
    new TestSets {
      val u = union(
                    union(
                      union(
                        union(
                          union(singletonSet(1), singletonSet(3)), 
                          singletonSet(4)
                        ), 
                        singletonSet(5)
                      ), 
                      singletonSet(7)
                    ), 
                    singletonSet(1000)
                  )

      val mapped = map(u, x => x - 1)

      printSet(u)
      printSet(mapped)
      assert(contains(mapped, 0), "doesn't contains 0")
      assert(contains(mapped, 2), "doesn't contains 2")
      assert(contains(mapped, 3), "doesn't contains 3")
      assert(contains(mapped, 4), "doesn't contains 4")
      assert(contains(mapped, 6), "doesn't contains 6")
      assert(contains(mapped, 999), "doesn't contains 999")
    }
  }

}
