package tendaysstat

import tendaysstat.day7Correlation.spearman
import scala.math.pow

object day8SimpleRegression {

  def day8RegressionLine(): Unit = {
    val request = 80
    val math = Array(95, 85, 80, 70, 60)
    val phys = Array(85, 95, 70, 65, 70)
    val n = 5
    var sumMath = 0
    var sumPhys = 0
    var sumMathSq = 0
    var sumMathPhys = 0
    for(i <- 0 until n){
      sumMath += math(i)
      sumPhys += phys(i)
      sumMathSq += (math(i) * math(i))
      sumMathPhys += (math(i) * phys(i))
    }
    val meanMath = sumMath / n
    val meanPhys = sumPhys / n
    val bNumerator = (n * sumMathPhys) - (sumMath * sumPhys)
    val bDenominator = (n * sumMathSq) - pow(sumMath, 2)
    val b = bNumerator / bDenominator
    // a = meanY - (b * meanX),
    val a = meanPhys - (b * meanMath)
    // regression Y = a + bX
    val result = a + b * request
    println("%.3f".format(result))
  }
}
