package org.creek.geometry

class KDBox[T](val coordinates: Array[(T, T)])(implicit ordering: Ordering[T]) {

  def contains(other: KDBox[T]): Boolean = ???
  def contains(point: KDPoint[T]): Boolean = ???
  def intersects(other: KDBox[T]): KDBox[T] = ???
  def distanceTo(point: KDPoint[T]): T = ???

}
