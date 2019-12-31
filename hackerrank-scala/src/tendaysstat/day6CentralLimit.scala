package tendaysstat

import scala.math.{abs, exp, sqrt}

object day6CentralLimit {

  def CLT1(): Unit = {
    val elevatorWeightCapacity = 9800
    val boxes = 49
    val boxWeightMean = 205
    val boxWeightSd = 15
    val sampleMean = boxes * boxWeightMean
    val sampleSD = Math.sqrt(boxes) * boxWeightSd
    // cdf x >= 9800 is the max point at which box will go in the elevator,
    // and therefore is the prob for being put in the elevator
    val probForBoxesToGoInElevator = cdf(elevatorWeightCapacity, sampleMean, sampleSD)
    println("%.4f".format(probForBoxesToGoInElevator))
  }

  def CLT2(): Unit = {
    val ticketsPerStudentMean = 2.4
    val ticketPerStudentSd = 2.0
    val ticketsLeft = 250
    val studentsLeft = 100
    val sampleMean = studentsLeft * ticketsPerStudentMean
    val sampleSD = Math.sqrt(studentsLeft) * ticketPerStudentSd
    // cdf x >= 250 is the max prob of students getting N of tickets they want
    val probAllStudentsWillGetWhatTheyWant = cdf(ticketsLeft, sampleMean, sampleSD)
    println("%.4f".format(probAllStudentsWillGetWhatTheyWant))
  }

  def CLT3(): Unit = {
    val n = 100
    val mean = 500
    val sd = 80
    val zscore = 1.96
    // confidence interval
    val ci = zscore * sd / Math.sqrt(n)
    // interval covering 95% of distribution
    println("%.2f".format(mean - ci))
    println("%.2f".format(mean + ci))
  }

  private def cdf(x: Double, mean: Double, sd: Double) = {
    val z = (x - mean) / (sd * sqrt(2))
    0.5 * (1 + err(z))
  }

  /* http://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html */
  private def err(z: Double) = {
    val t = 1.0 / (1.0 + 0.5 * abs(z))
    // use Horner's method
    val ans = 1 - t * exp(-z * z - 1.26551223 + t * (1.00002368 + t * (0.37409196 + t * (0.09678418 + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398 + t * (1.48851587 + t * (-0.82215223 + t * 0.17087277)))))))))
    if (z >= 0) ans
    else -ans
  }
  
}
