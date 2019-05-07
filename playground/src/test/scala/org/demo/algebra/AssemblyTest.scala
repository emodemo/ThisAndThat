package org.demo.algebra

import org.scalatest.FunSuite

class AssemblyTest extends FunSuite {
  // base
  test("increment") { assert(Assembly.incr(1) === 2)}
  test("zero") { assert(Assembly.zero() === 0) }
  // added
  test("faux") {assert(Assembly.faux() === 0)}
  test("vraie") {assert(Assembly.vraie() === 1)}
  // IS FALSE
  test("isZero0") {assert(Assembly.isZero(0) === 1)}
  test("isZero1") {assert(Assembly.isZero(1) === 0)}
  test("isZero2") {assert(Assembly.isZero(2) === 0)}
  // IS TRUE
  test("isOne0") {assert(Assembly.isOne(0) === 0)}
  test("isOne1") {assert(Assembly.isOne(1) === 1)}
  test("isOne2") {assert(Assembly.isOne(2) === 1)}
  // BASE OPERATIONS
  test("decr") {assert(Assembly.decr(2) === 1)}
  test("add") {assert(Assembly.add(3, 2) === 5)}
  test("sub") {assert(Assembly.sub(3, 2) === 1)}
  test("modulo_a") {assert(Assembly.mod(8,5) === 3)}
  test("modulo_b") {assert(Assembly.mod(5,2) === 1)}
  test("mul") {assert(Assembly.sub(3, 2) === 1)}
  test("div_ceil") {assert(Assembly.div_ceil(5, 2) === 3)}
  test("div_floor") {assert(Assembly.div_floor(5, 2) === 2)}
  test("div_floor_v2") {assert(Assembly.div_floor_v2(5, 2) === 2)}
  test("div_floor_thru_ceil") {assert(Assembly.div_floor_thru_ceil(5, 2) === 2)}
  test("rem") {assert(Assembly.rem(5, 2) === 1)}
  test("rem_test2") {assert(Assembly.rem(43, 5) === 3)}
  // LTE
  test("lte_less") {assert(Assembly.lte(1, 2) === 1)}
  test("lte_eq") {assert(Assembly.lte(2, 2) === 1)}
  test("lte_more") {assert(Assembly.lte(3, 2) === 0)}
  // GTE
  test("gte_less") {assert(Assembly.gte(1, 2) === 0)}
  test("gte_eq") {assert(Assembly.gte(2, 2) === 1)}
  test("gte_more") {assert(Assembly.gte(3, 2) === 1)}
  // GT
  test("gt_less") {assert(Assembly.gt(1, 2) === 0)}
  test("gt_eq") {assert(Assembly.gt(2, 2) === 0)}
  test("gt_more") {assert(Assembly.gt(3, 2) === 1)}
  // LT
  test("lt_less") {assert(Assembly.lt(1, 2) === 1)}
  test("lt_eq") {assert(Assembly.lt(2, 2) === 0)}
  test("lt_more") {assert(Assembly.lt(3, 2) === 0)}
  // EQ
  test("eq_less") {assert(Assembly.eq(1, 2) === 0)}
  test("eq_eq") {assert(Assembly.eq(2, 2) === 1)}
  test("eq_more") {assert(Assembly.eq(3, 2) === 0)}
  // NEQ
  test("neq_less") {assert(Assembly.neq(1, 2) === 1)}
  test("neq_eq") {assert(Assembly.neq(2, 2) === 0)}
  test("neq_more") {assert(Assembly.neq(3, 2) === 1)}
}
