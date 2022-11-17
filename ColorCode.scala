/*                                                                            */
/*      ***************************************************************       */
/*      ***       Resistor ColourCode Database  - by Her4sov -      ***       */
/*      see:  https://en.wikipedia.org/wiki/Electronic_color_code   ***       */
/*      ***               (c) Apache 2.0 License                    ***       */
/*      ***************************************************************       */
/*                                                                            */

package herasov
package marking
package ResistorColorCode

import scala.collection.immutable.List
import scala.collection.mutable.{ArrayBuffer, HashMap}
import scala.Option

type ColorCodeTuple =
/*******************************************************************************
*            *                *             *                *                 *
*  Colour    *  Abbreviature  *   Signifi-  *   Tolerance,   *   Temperature   *
*            *                *    cant or  *       %        *   coefficient,  *
*            *                *   exponent  *                *      ppm/K     *
*            *                *             *                *                 *
*******************************************************************************/
(  String    ,     String     ,     Int     , Option[Double] ,  Option[Int]    )
/******************************************************************************/
val colorCode: List[ColorCodeTuple] = List(
/******************************************************************************/
(  "pink"    ,      "pk"      ,     -3      ,  Option.empty  ,  Option.empty)  ,
(  "silver"  ,      "sr"      ,     -2      ,  Option(10)    ,  Option.empty)  ,
(  "gold"    ,      "gd"      ,     -1      ,  Option(5)     ,  Option.empty)  ,
(  "black"   ,      "bk"      ,      0      ,  Option.empty  ,  Option(250))   ,
(  "brown"   ,      "bn"      ,      1      ,  Option(1)     ,  Option(100))   ,
(  "red"     ,      "rd"      ,      2      ,  Option(2)     ,  Option(50))    ,
(  "orange"  ,      "og"      ,      3      ,  Option(0.05)  ,  Option(15))    ,
(  "yellow"  ,      "ye"      ,      4      ,  Option(0.02)  ,  Option(25))    ,
(  "green"   ,      "gn"      ,      5      ,  Option(0.5)   ,  Option(20))    ,
(  "blue"    ,      "bu"      ,      6      ,  Option(0.25)  ,  Option(10))    ,
(  "violet"  ,      "vt"      ,      7      ,  Option(0.1)   ,  Option(5))     ,
(  "grey"    ,      "gy"      ,      8      ,  Option(0.01)  ,  Option(1))     ,
(  "white"   ,      "wh"      ,      9      ,  Option.empty  ,  Option.empty)  )
/******************************************************************************/

type ColorCodeInternals = (
  ArrayBuffer[String], ArrayBuffer[String], HashMap[String, Int]
) 

object ColorCode {
  def ks() = new ArrayBuffer[String](colorCode.length)
  def acc(): ColorCodeInternals = (ks(), ks(), new HashMap)
  def relation = colorCode.foldLeft(acc()) {
    (a: ColorCodeInternals, i: ColorCodeTuple ) => (a, i) match {
      case ((codes, abbrs, values), (code: String,
        abbr: String, value: Int, _, _)) => {
          codes += code
          abbrs += abbr
          values(code) = value
          values(abbr) = value
        }
      (codes, abbrs, values)
    }
  }
  val colors = relation._1.toSeq
  val abbrs = relation._2.toSeq
  val values = relation._3.toMap
}

object Test:
  def main(args: Array[String]) = {
    assert(ColorCode.values("green") == 5)
    assert(ColorCode.values("wh") == 9)
    assert(ColorCode.values("pink") == -3)
    assert(ColorCode.colors(0) == "pink")
    assert(ColorCode.abbrs(12) == "wh")
    println(".....")
  }

