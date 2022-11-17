/*                                                                            */
/*      ***************************************************************       */
/*      ***       Resistor ColourCode Database  - by Her4sov -      ***       */
/*      see:  https://en.wikipedia.org/wiki/Electronic_color_code   ***       */
/*      ***               (c) Apache 2.0 License                    ***       */
/*      ***************************************************************       */
/*                                                                            */
package resistance

import scala.collection.immutable.List
import scala.collection.mutable.{ArrayBuffer, HashMap}
import scala.Option

type ColorCodeTuple = 
/*******************************************************************************
*            *                *             *                *                 *
*  Colour    *  Abbreviature  *  Signifi-   *   Tolerance,   *   Temperature   *
*            *                *   cant or   *        %       *   coefficient,  *
*            *                *  exponent   *                *      ppm/K      *
*            *                *             *                *                 *
*******************************************************************************/
(   String   ,      String    ,    Int      , Option[Double] ,   Option[Int]   )
/******************************************************************************/
val colorCodeList: Seq[ColorCodeTuple] = List(
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

case class ColorCodeGrouped (
  colors: ArrayBuffer[String],
  abbrs: ArrayBuffer[String],
  namesToInt: HashMap[String, Int]
)

object ColorCodeGrouper {
  val colorCode = colorCodeList
  def apply(ungrouped: Seq[ColorCodeTuple]): ColorCodeGrouped = {
    def ks = new ArrayBuffer[String](colorCode.size)
    val grouped = ColorCodeGrouped(ks, ks, HashMap())
    ungrouped.foldLeft(grouped) ((acc: ColorCodeGrouped, i: ColorCodeTuple) =>
    (acc, i) match {
      case (ColorCodeGrouped(codes, abbrs, values), (c, a, v, _, _)) => {
        codes += c
        abbrs += a
        values(c) = v
        values(a) = v
        acc
      }
    })
  }
}

