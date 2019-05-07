package org.demo.algebra

/**
  * Valid for Natural numbers only
  * https://stackoverflow.com/questions/34829670/relational-operations-using-only-increment-loop-assign-zero
  * https://stackoverflow.com/questions/39809914/divide-operation-using-increment-loop-assign-zero
  */
object Assembly {

  val incr: Int => Int = (x: Int) => x + 1 // : Int => Int is the type annotation part
  val zero: () => Int = () => 0 // val zero = () => 0
  // loop(x)
  // assign(x,Y)

  val faux = () => zero()
  val vraie = () => incr(faux())
  /** is False */
  val isZero = (x: Int) => {
    var z = vraie()
    for(_ <- 1 to x){ z = faux() }
    z
  }
  /** is True */
  val isOne = (x: Int) => {
    var z = faux()
    for(_ <- 1 to x){ z = vraie() }
    z
  }
  val decr = (x: Int) => {
    var z = zero() // in the loop => x-1 times z
    var z2 = zero() // in the loop => z times z2
    for(_ <- 1 to x) { z = z2; z2 = incr(z2)}
    z
  }
  val add = (x: Int, y: Int) => {
    var tmpY = y // assign
    for(_ <- 1 to x) { tmpY = incr(tmpY) }
    tmpY
  }
  val sub = (x: Int, y: Int) => {
    var tmpX = x // assign
    for(_ <- 1 to y) { tmpX = decr(tmpX)}
    tmpX
  }

  // while(z >= y) { z -= y }
  val mod = (x: Int, y: Int) => {
    var z = x
    for(_ <- 1 to x) {
      val go = gte(z, y)
      for(_ <- 1 to go){
        z = sub(z, y)
      }
    }
    z
  }

  val mul = (x: Int, y: Int) => {
    var z = zero()
    for(_ <- 1 to x) { z = add(z, y)}
    z
  }

  /** ceiling division: 5/2 = 3 */
  val div_ceil = (x: Int, y: Int) => {
    var z = zero()
    var tmpX = x // assign
    // if(z2 > 0) z++
    for(_ <- 1 to x){
      val l = gt(tmpX, zero()) // if(z2>0) 1 else 0
      z = add(z, l) // z+1 or Z+0
      tmpX = sub(tmpX, y) // tmpX - y
    }
    z
  }
  /** floor division: 5/2 = 2 */
  val div_floor = (x: Int, y: Int) => {
    var z = zero()
    var z2 = zero()
    for(_ <- 1 to x){
      z2 = add(z2, y) // 0+2, 2+2, 4+2 ...
      val l = lte(z2, x) // 2<5 => 1, 4<5 => 1, 6>5 => 0 ...
      z = add(z, l) // 0+1; 1+1; 2+0 ...
    }
    z
  }
  /** floor division: 5/2 = 2 */
  val div_floor_v2 = (x: Int, y: Int) => {
    var z = zero()
    var tmpX = incr(x) // assign
    for(_ <- 1 to x) {
      tmpX = sub(tmpX, y) // 6-2=4, 4-2=2, 2-2=0
      val l = isOne(tmpX) // 1, 1, 0 ...
      z = add(z, l)
    }
    z
  }
  /** floor division: 5/2 = 2 */
  val div_floor_thru_ceil = (x: Int, y: Int) => {
    val c1 = div_ceil(x, y) // 5/2 = 3
    val c2 = div_ceil(incr(x), y) // 6/2 = 3
    val e = eq(c1, c2) // 3=3 => eq = 1
    sub(c1, e) // return 2
  }

  /** for a/d = q + remainder , a = q*d + r, therefore r = a - q*d */
  val rem = (x: Int, y: Int) => {
    val q = div_floor(x, y)
    sub(x, mul(q, y)) // a - d*q
  }

  /** only naturals => 4-5 = 0*/
  val lte = (x: Int, y: Int) => isZero(sub(x, y))
  /** z >= y is the same as y <= z */
  val gte = (x: Int, y: Int) => lte(y, x)
  /** if x > y therefore x <= y is false*/
  val gt = (x: Int, y: Int) => isZero(lte(x, y))
  /** x < y is the same as y > x*/
  val lt = (x: Int, y: Int) => gt(y, x)
  /** if x <= y and y <= x therefore x==y ; multiply to get AND*/
  val eq = (x: Int, y: Int) => mul(lte(x, y), lte(y, x))
  /** */
  val neq = (x: Int, y: Int) => isZero(eq(x, y))

  // TODO: isPrime
}
